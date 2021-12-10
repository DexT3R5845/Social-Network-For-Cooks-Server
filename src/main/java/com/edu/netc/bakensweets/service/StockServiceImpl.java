package com.edu.netc.bakensweets.service;


import com.edu.netc.bakensweets.exception.BadRequestParamException;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.Stock;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.StockRepository;
import com.edu.netc.bakensweets.service.interfaces.StockService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;

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

    @Override
    public void deleteFromStock(long accountId, long ingredientId) {
        Stock stock = stockRepository.findByAccountAndIngredient(accountId, ingredientId);
        if (stock != null) {
            stockRepository.deleteById(stock.getId());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient with id %s or user with id %s not found.", ingredientId, accountId));
        }
    }
}
