package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.DishDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;

import java.util.List;

public interface DishService {
    void createDish(DishDTO dish);
    DishDTO getDishById(long id);
    void updateDish(long id, DishDTO dishDTO);
    void deleteDish(long id);
    PaginationDTO<DishDTO> getFilteredDishes(int pageSize, int currentPage, String name, List<String> categories, List<String> ingredients, boolean order);
    PaginationDTO<DishDTO> getDishesByStock(long id, int pageSize, int currentPage);
}
