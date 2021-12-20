package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.*;

import java.util.Collection;
import java.util.List;

public interface DishService {
    void createDish(DishDTO<DishIngredientDTO, DishKitchenwareDTO> dish);
    DishInfoDTO<DishIngredientInfoDTO, DishKitchenwareInfoDTO> getDishById(String email, long id);
    void updateDish(long id, DishDTO<DishIngredientDTO, DishKitchenwareDTO> dishDTO);
    void deleteDish(long id);
    Collection<String> getDishCategories();
    PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>> getFilteredDishes(String email, int pageSize, int currentPage, String name, List<String> categories, List<String> ingredients, boolean order);
    PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>> getDishesByStock(String email, int pageSize, int currentPage);
    PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>> getFavoriteDishes(String email, int pageSize, int currentPage);
    void updateLike(String email, long dishId, boolean isLiked);
    void updateFavorite(String email, long dishId, boolean isFavorite);

}
