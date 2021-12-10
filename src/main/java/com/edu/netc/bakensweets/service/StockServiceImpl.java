package com.edu.netc.bakensweets.service;


import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.dto.StockIngredientDTO;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Stock;
import com.edu.netc.bakensweets.model.form.SearchStockIngredientModel;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.StockRepository;
import com.edu.netc.bakensweets.service.interfaces.StockService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public void addToStock(String accountEmail, long ingredientId, int amount) {
        try {
            Account account = accountRepository.findByEmail(accountEmail);
            Stock stock = new Stock(account.getId(), ingredientId, amount);
            if (stockRepository.findByAccountAndIngredient(account.getId(), ingredientId) == null) {
                stockRepository.create(stock);
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient id %s already in stock.", ingredientId));
            }
        } catch (DataIntegrityViolationException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient with id %s not found.", ingredientId));
        }
    }

    @Transactional
    @Override
    public void deleteFromStock(String accountEmail, long ingredientId) {
        Account account = accountRepository.findByEmail(accountEmail);
        Stock stock = stockRepository.findByAccountAndIngredient(account.getId(), ingredientId);
        if (stock != null) {
            stockRepository.deleteById(stock.getId());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient with id %s not found.", ingredientId));
        }
    }

    @Transactional
    @Override
    public void updateIngredientFromStock(long accountId, long ingredientId, int amount) {
        Stock stock = stockRepository.findByAccountAndIngredient(accountId, ingredientId);
        if (stock != null) {
            stock.setAmount(amount);
            stockRepository.update(stock);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient with id %s or user with id %s not found.", ingredientId, accountId));
        }
    }

    @Transactional
    @Override
    public void deleteFromStock(long accountId, long ingredientId) {
        Stock stock = stockRepository.findByAccountAndIngredient(accountId, ingredientId);
        if (stock != null) {
            stockRepository.deleteById(stock.getId());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient with id %s or user with id %s not found.", ingredientId, accountId));
        }
    }

    @Transactional
    @Override
    public PaginationDTO<StockIngredientDTO> getIngredientsFromStock(int size, int currentPage, String search, boolean order,
                                                                     String sortBy, List<String> ingredientCategory, String accountEmail) {
        Account account = accountRepository.findByEmail(accountEmail);
        String orderStr = order ? "ASC" : "DESC";
        SearchStockIngredientModel searchStockIngredient = new SearchStockIngredientModel(account.getId(), search, ingredientCategory,
                orderStr, sortBy, currentPage * size, size);
        int stockSize = stockRepository.countAllIngredientsInStock(searchStockIngredient);
        Collection<StockIngredientDTO> stock = stockRepository.findAllIngredientsInStock(searchStockIngredient);
        return new PaginationDTO<StockIngredientDTO> (stock, stockSize);
    }

    @Transactional
    @Override
    public PaginationDTO<AccountPersonalInfoDTO> getAccountsWithStock(int size, int currentPage) {
        Collection<Account> accounts = stockRepository.findAllAccountsWithStock( size, currentPage * size);
        return new PaginationDTO<>(accountMapper.accountsToPersonalInfoDtoCollection(accounts), accounts.size());
    }


}
