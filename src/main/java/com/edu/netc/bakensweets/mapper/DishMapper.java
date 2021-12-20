package com.edu.netc.bakensweets.mapper;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.model.Dish;
import com.edu.netc.bakensweets.model.DishIngredient;
import com.edu.netc.bakensweets.model.DishKitchenware;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mappings({
            @Mapping(target="dishName", source="dto.dishName"),
            @Mapping(target = "dishCategory", source = "dto.dishCategory"),
            @Mapping(target = "imgUrl", source = "dto.imgUrl"),
            @Mapping(target = "description", source = "dto.description"),
            @Mapping(target = "receipt", source = "dto.receipt"),
            @Mapping(target = "dishType", source = "dto.dishType")
    })
    Dish dishDtoToDish(DishDTO<DishIngredientDTO, DishKitchenwareDTO> dto);

    @Mappings({
            @Mapping(target="id", source="dish.id"),
            @Mapping(target="dishName", source="dish.dishName"),
            @Mapping(target = "dishCategory", source = "dish.dishCategory"),
            @Mapping(target = "imgUrl", source = "dish.imgUrl"),
            @Mapping(target = "description", source = "dish.description"),
            @Mapping(target = "receipt", source = "dish.receipt"),
            @Mapping(target = "dishType", source = "dish.dishType"),
            @Mapping(target = "totalLikes", source = "dish.totalLikes")

//            ,
//            @Mapping(target = "isLiked", source = "dish.isLiked"),
//            @Mapping(target = "isFavorite", source = "dish.isFavorite")
    })
    DishInfoDTO<DishIngredientInfoDTO, DishKitchenwareInfoDTO> dishToDishDto(Dish dish);

    @Mappings({
            @Mapping(target="id", source="dishes.id"),
            @Mapping(target="dishName", source="dishes.dishName"),
            @Mapping(target = "dishCategory", source = "dishes.dishCategory"),
            @Mapping(target = "imgUrl", source = "dishes.imgUrl"),
            @Mapping(target = "description", source = "dishes.description"),
            @Mapping(target = "receipt", source = "dishes.receipt"),
            @Mapping(target = "dishType", source = "dishes.dishType"),
            @Mapping(target = "totalLikes", source = "dishes.totalLikes"),
            @Mapping(target = "isLiked", source = "dishes.isLiked"),
            @Mapping(target = "isFavorite", source = "dishes.isFavorite")
    })
    Collection<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>> dishToDishDtoCollection(Collection<Dish> dishes);



    @Mappings({
            @Mapping(target="id", source="ingredientsDTO.id"),
            @Mapping(target="name", source="ingredientsDTO.name"),
            @Mapping(target = "imgUrl", source = "ingredientsDTO.imgUrl"),
            @Mapping(target = "ingredientCategory", source = "ingredientsDTO.ingredientCategory"),
            @Mapping(target = "active", source = "ingredientsDTO.active"),
            @Mapping(target = "amount", source = "ingredientsDTO.amount")
    })
    List<DishIngredient> dishIngredientsDtoToDishIngredients (Collection<DishIngredientDTO> ingredientsDTO);

    @Mappings({
            @Mapping(target="id", source="kitchenwaresDTO.id"),
            @Mapping(target="name", source="kitchenwaresDTO.name"),
            @Mapping(target = "imgUrl", source = "kitchenwaresDTO.imgUrl"),
            @Mapping(target = "active", source = "kitchenwaresDTO.active"),
            @Mapping(target = "category", source = "kitchenwaresDTO.category"),
            @Mapping(target = "amount", source = "kitchenwaresDTO.amount")
    })
    List<DishKitchenware> dishKitchenwaresDtoToDishKitchenwares (Collection<DishKitchenwareDTO> kitchenwaresDTO);

    @Mappings({
            @Mapping(target="id", source="ingredients.id"),
            @Mapping(target="name", source="ingredients.name"),
            @Mapping(target = "imgUrl", source = "ingredients.imgUrl"),
            @Mapping(target = "ingredientCategory", source = "ingredients.ingredientCategory"),
            @Mapping(target = "active", source = "ingredients.active"),
            @Mapping(target = "amount", source = "ingredients.amount")
    })
    Collection<DishIngredientInfoDTO> dishIngredientsToDishIngredientsDto(Collection<DishIngredient> ingredients);

    @Mappings({
            @Mapping(target="id", source="kitchenwareList.id"),
            @Mapping(target = "imgUrl", source = "kitchenwareList.imgUrl"),
            @Mapping(target = "active", source = "kitchenwareList.active"),
            @Mapping(target="name", source="kitchenwareList.name"),
            @Mapping(target = "category", source = "kitchenwareList.category"),
            @Mapping(target = "amount", source = "kitchenwareList.amount")
    })
    Collection<DishKitchenwareInfoDTO> dishKitchenwaresToDishKitchenwaresDto (Collection<DishKitchenware> kitchenwareList);
}
