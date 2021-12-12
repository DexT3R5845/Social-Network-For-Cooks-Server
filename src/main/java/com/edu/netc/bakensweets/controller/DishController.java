package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.DishDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.service.interfaces.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    private final DishService dishService;

    public DishController (DishService dishService) {
        this.dishService = dishService;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping(value = "/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable long id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping(value = "")
    public void createDish(@Valid @RequestBody DishDTO dish) {
        dishService.createDish(dish);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/{id}")
    public void updateDish(@PathVariable long id, @Valid @RequestBody DishDTO dish) {
        dishService.updateDish(id, dish);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @DeleteMapping(value = "/{id}")
    public void deleteDish(@PathVariable long id) {
        dishService.deleteDish(id);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping(value = "")
    public ResponseEntity<PaginationDTO<DishDTO>> getFilteredDishes(
            @RequestParam(value = "pageSize") @Min(value = 1, message = "Page size must be higher than 0") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) @Min(value = 0, message = "Current page must be higher than 0") int currentPage,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "categories", required = false) List<String> categories,
            @RequestParam(value = "ingredients",  required = false) List<String> ingredients, // массив айди ингредиентов
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order) {
        return ResponseEntity.ok(dishService.getFilteredDishes(pageSize, currentPage, name, categories, ingredients, order));
    }
}
