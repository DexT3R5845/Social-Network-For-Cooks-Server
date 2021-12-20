package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.DishMapper;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.model.Dish;
import com.edu.netc.bakensweets.model.DishIngredient;
import com.edu.netc.bakensweets.model.DishKitchenware;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.repository.interfaces.DishRepository;
import com.edu.netc.bakensweets.service.interfaces.DishService;
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
    private final CredentialsRepository credentialsRepository;

    public DishServiceImpl (DishRepository dishRepository, DishMapper dishMapper, CredentialsRepository credentialsRepository) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    @Transactional
    public void createDish (DishDTO<DishIngredientDTO, DishKitchenwareDTO> dishDto) {
        Dish dish = dishMapper.dishDtoToDish(dishDto);
        try {
            long id = dishRepository.create(dish);
            dishRepository.createDishIngredient(dishMapper.dishIngredientsDtoToDishIngredients(dishDto.getIngredients()), id);
            dishRepository.createDishKitchenware(dishMapper.dishKitchenwaresDtoToDishKitchenwares(dishDto.getKitchenwares()), id);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot create dish, incorrect input parameters");
        }
    }


    @Override
    public Collection<String> getDishCategories() {
        return dishRepository.getDishCategories();
    }


    @Override
    public DishInfoDTO<DishIngredientInfoDTO, DishKitchenwareInfoDTO> getDishById (String email, long id) {
        try {
            Credentials account = credentialsRepository.findByEmail(email);
            Dish dish = dishRepository.findById(account.getId(), id);
            DishInfoDTO<DishIngredientInfoDTO, DishKitchenwareInfoDTO> dishDto = dishMapper.dishToDishDto(dish);
            dishDto.setIsLiked(dish.getIsLiked());
            dishDto.setIsFavorite(dish.getIsFavorite());

            Collection<DishIngredient> ingredients = dishRepository.findIngredientsByDishId(id);
            dishDto.setIngredients(dishMapper.dishIngredientsToDishIngredientsDto(ingredients));

            Collection<DishKitchenware> kitchenwares = dishRepository.findKitchenwaresByDishId(id);
            dishDto.setKitchenwares(dishMapper.dishKitchenwaresToDishKitchenwaresDto(kitchenwares));
            return dishDto;

        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Dish was not found.");
        }
    }


    @Override
    @Transactional
    public void updateDish(long id, DishDTO<DishIngredientDTO, DishKitchenwareDTO> dishDTO) { //TODO THINK ABOUT ANOTHER WAY TO DO THIS (maybe UPSERT or getting flag from client)
        try {
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
    public PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>> getFilteredDishes (
            String email, int pageSize, int currentPage, String name, List<String> categories, List<String> ingredients, boolean order) {

        name = "%" + name + "%";
        Credentials account = credentialsRepository.findByEmail(email);
        int totalElements = dishRepository.countFilteredDishes(name, categories, ingredients);
        Collection<Dish> dishes = dishRepository.findAllDishes(
                account.getId(), name, categories, ingredients, order,  pageSize, pageSize * currentPage
        );
        return new PaginationDTO<>(dishMapper.dishToDishDtoCollection(dishes), totalElements);
    }


    @Override
    public PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>> getDishesByStock(String email, int pageSize, int currentPage) {
        Credentials account = credentialsRepository.findByEmail(email);
        int totalElements = dishRepository.countDishesByStock(account.getId());
        Collection<Dish> dishes = dishRepository.getDishesByStock(account.getId(),  pageSize, pageSize * currentPage);
        return new PaginationDTO<>(dishMapper.dishToDishDtoCollection(dishes), totalElements);
    }

    @Override
    public PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>> getFavoriteDishes(String email, int pageSize, int currentPage) {
        int totalElements = dishRepository.countFavoriteDishes(email);
        Collection<Dish> dishes = dishRepository.getFavoriteDishes(email,  pageSize, pageSize * currentPage);
        return new PaginationDTO<>(dishMapper.dishToDishDtoCollection(dishes), totalElements);
    }


    @Override
    public void updateLike (String email, long dishId, boolean isLiked) {
        try {
            dishRepository.changeDishLike(email, dishId, isLiked);
        } catch (DataAccessException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Database error");
        }
    }


    @Override
    public void updateFavorite (String email, long dishId, boolean isFavorite) {
        try {
            dishRepository.changeDishFavorite(email, dishId, isFavorite);
        } catch (DataAccessException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Database error");
        }
    }
}
