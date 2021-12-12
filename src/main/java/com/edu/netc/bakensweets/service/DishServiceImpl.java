package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.DishDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.DishMapper;
import com.edu.netc.bakensweets.model.Dish;
import com.edu.netc.bakensweets.model.DishIngredient;
import com.edu.netc.bakensweets.model.DishKitchenware;
import com.edu.netc.bakensweets.repository.interfaces.DishRepository;
import com.edu.netc.bakensweets.service.interfaces.DishService;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    public DishServiceImpl (DishRepository dishRepository, DishMapper dishMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
    }

    @Override
    @Transactional
    public void createDish (DishDTO dishDto) {
        Long id = Utils.generateUniqueId();
        Dish dish = dishMapper.dishDtoToDish(dishDto);
        dish.setId(id);
        try {
            dishRepository.create(dish);
            dishRepository.createDishIngredient(dishMapper.dishIngredientsDtoToDishIngredients(dishDto.getIngredients()), id);
            dishRepository.createDishKitchenware(dishMapper.dishKitchenwaresDtoToDishKitchenwares(dishDto.getKitchenwares()), id);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot create dish, incorrect input parameters");
        }
    }

    @Override
    public DishDTO getDishById (long id) {
        try {
            DishDTO dish = dishMapper.dishToDishDto(dishRepository.findById(id));

            Collection<DishIngredient> ingredients = dishRepository.findIngredientsByDishId(id);
            dish.setIngredients(dishMapper.dishIngredientsToDishIngredientsDto(ingredients));

            Collection<DishKitchenware> kitchenwares = dishRepository.findKitchenwaresByDishId(id);
            System.err.println(kitchenwares);

            dish.setKitchenwares(dishMapper.dishKitchenwaresToDishKitchenwaresDto(kitchenwares));
            System.err.println(dish.getKitchenwares());

            return dish;
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Dish was not found.");
        }
    }

    @Override
    @Transactional
    public void updateDish(long id, DishDTO dishDTO) { //TODO THINK ABOUT ANOTHER WAY TO DO THIS (maybe UBSERT or getting flag from client)
        try { //DELETING ALL OLD KITCHENWARES/INGREDIENTS BEFORE INSERTING NEW LISTS
            dishRepository.deleteIngredientsByDishId(id);
            dishRepository.deleteKitchenwaresByDishId(id);

            Dish dish = dishMapper.dishDtoToDish(dishDTO);
            dish.setId(id);
            dishRepository.update(dish);
            dishRepository.createDishIngredient(dishMapper.dishIngredientsDtoToDishIngredients(dishDTO.getIngredients()), id);
            dishRepository.createDishKitchenware(dishMapper.dishKitchenwaresDtoToDishKitchenwares(dishDTO.getKitchenwares()), id);

        } catch (DataAccessException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "cannot find current dish");
        }
    }

    @Override
    public void deleteDish(long id) {
        try {
            dishRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "cannot find current dish");
        }
    }

    @Override
    public PaginationDTO<DishDTO> getFilteredDishes (int pageSize, int currentPage, String name, List<String> categories, List<String> ingredients, boolean order) {
        name = "%" + name + "%";
        int totalElements = dishRepository.countFilteredDishes(name, categories, ingredients);
        Collection<Dish> dishes = dishRepository.findAllDishes(
                name, categories, ingredients, order,  pageSize, pageSize * currentPage
        );
        return new PaginationDTO<>(dishMapper.dishToDishDtoCollection(dishes), totalElements);
    }

    @Override
    public PaginationDTO<DishDTO> getDishesByStock(long id, int pageSize, int currentPage) {
        int totalElements = dishRepository.countDishesByStock(id);
        Collection<Dish> dishes = dishRepository.getDishesByStock(id,  pageSize, pageSize * currentPage);
        return new PaginationDTO<>(dishMapper.dishToDishDtoCollection(dishes), totalElements);
    }
}
