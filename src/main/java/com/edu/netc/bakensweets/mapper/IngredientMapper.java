package com.edu.netc.bakensweets.mapper;

import com.edu.netc.bakensweets.dto.IngredientDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient ingredientDTOtoIngredient(IngredientDTO ingredientDTO);
}
