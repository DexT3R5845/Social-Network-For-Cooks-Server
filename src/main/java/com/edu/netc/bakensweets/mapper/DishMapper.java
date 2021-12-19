package com.edu.netc.bakensweets.mapper;

import com.edu.netc.bakensweets.dto.DishDTO;
import com.edu.netc.bakensweets.dto.DishIngredientDTO;
import com.edu.netc.bakensweets.dto.DishKitchenwareDTO;
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
            @Mapping(target="id", source="dto.id"),
            @Mapping(target="dishName", source="dto.dishName"),
            @Mapping(target = "dishCategory", source = "dto.dishCategory"),
            @Mapping(target = "imgUrl", source = "dto.imgUrl"),
            @Mapping(target = "description", source = "dto.description"),
            @Mapping(target = "receipt", source = "dto.receipt"),
            @Mapping(target = "dishType", source = "dto.dishType")
    })
    Dish dishDtoToDish(DishDTO dto);

    @Mappings({
            @Mapping(target="id", source="dish.id"),
            @Mapping(target="dishName", source="dish.dishName"),
            @Mapping(target = "dishCategory", source = "dish.dishCategory"),
            @Mapping(target = "imgUrl", source = "dish.imgUrl"),
            @Mapping(target = "description", source = "dish.description"),
            @Mapping(target = "receipt", source = "dish.receipt"),
            @Mapping(target = "dishType", source = "dish.dishType")
    })
    DishDTO dishToDishDto(Dish dish);

    @Mappings({
            @Mapping(target="id", source="dishes.id"),
            @Mapping(target="dishName", source="dishes.dishName"),
            @Mapping(target = "dishCategory", source = "dishes.dishCategory"),
            @Mapping(target = "imgUrl", source = "dishes.imgUrl"),
            @Mapping(target = "description", source = "dishes.description"),
            @Mapping(target = "receipt", source = "dishes.receipt"),
            @Mapping(target = "dishType", source = "dishes.dishType")
    })
    Collection<DishDTO> dishToDishDtoCollection(Collection<Dish> dishes);



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
    Collection<DishIngredientDTO> dishIngredientsToDishIngredientsDto(Collection<DishIngredient> ingredients);


//    @Mappings({  //TODO DELETE DUBLICATE AFTER RENAMING kitchenware
//            @Mapping(target="id", source="dishKitchenware.id"),
//            @Mapping(target = "imgUrl", source = "dishKitchenware.kitchwarImg"),
//            @Mapping(target = "active", source = "dishKitchenware.active"),
//            @Mapping(target="name", source="dishKitchenware.kitchwarName"),
//            @Mapping(target = "category", source = "dishKitchenware.kitchwarCategory"),
//            @Mapping(target = "amount", source = "dishKitchenware.amount")
//    })
//    DishKitchenwareDTO dishKitchenwareToDishKitchenwareDTO(DishKitchenware dishKitchenware);

    @Mappings({
            @Mapping(target="id", source="kitchenwareList.id"),
            @Mapping(target = "imgUrl", source = "kitchenwareList.imgUrl"),
            @Mapping(target = "active", source = "kitchenwareList.active"),
            @Mapping(target="name", source="kitchenwareList.name"),
            @Mapping(target = "category", source = "kitchenwareList.category"),
            @Mapping(target = "amount", source = "kitchenwareList.amount")
    })
    Collection<DishKitchenwareDTO> dishKitchenwaresToDishKitchenwaresDto (Collection<DishKitchenware> kitchenwareList);
}
