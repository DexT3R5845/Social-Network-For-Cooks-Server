package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.IngredientDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.form.SearchIngredientModel;
import com.edu.netc.bakensweets.repository.interfaces.IngredientRepository;
import com.edu.netc.bakensweets.service.interfaces.IngredientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void createIngredient(IngredientDTO ingredientDto) {

    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public PaginationDTO<Ingredient> getAllIngredients(SearchIngredientModel searchIngredientModel) {
        PageRequest pageRequest = PageRequest.of(searchIngredientModel.getNumPage(), searchIngredientModel.getSizePage());
        searchIngredientModel.setNumPage(pageRequest.getPageNumber());
        int totalElements = ingredientRepository.count();
        Collection<Ingredient> ingredients = ingredientRepository.findAll(searchIngredientModel);

        return new PaginationDTO<Ingredient>(ingredients, totalElements);
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {
        ingredientRepository.update(ingredient);
    }

    @Override
    public void updateStatus(Long id, boolean status) {
        ingredientRepository.updateStatus(id, status);
    }
}
