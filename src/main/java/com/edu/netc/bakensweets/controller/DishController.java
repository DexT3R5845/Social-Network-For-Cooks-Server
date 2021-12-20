package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.service.interfaces.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    private final DishService dishService;

    public DishController (DishService dishService) {
        this.dishService = dishService;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/categories")
    public ResponseEntity<Collection<String>> getAllCategories() {
        return ResponseEntity.ok(dishService.getDishCategories());
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<DishInfoDTO<DishIngredientInfoDTO, DishKitchenwareInfoDTO>> getDishById(
            @PathVariable long id,
            Principal principal) {
        return ResponseEntity.ok(dishService.getDishById(principal.getName(), id));
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping(value = "")
    public void createDish(@Valid @RequestBody DishDTO<DishIngredientDTO, DishKitchenwareDTO> dish) {
        dishService.createDish(dish);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/{id}")
    public void updateDish(@PathVariable long id, @Valid @RequestBody DishDTO<DishIngredientDTO, DishKitchenwareDTO> dish) {
        dishService.updateDish(id, dish);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public void deleteDish(@PathVariable long id) {
        dishService.deleteDish(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "")
    public ResponseEntity<PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>>> getFilteredDishes(
            Principal principal,
            @RequestParam(value = "pageSize") @Min(value = 1, message = "Page size must be higher than 0") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int currentPage,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "categories", required = false) List<String> categories,
            @RequestParam(value = "ingredients",  required = false) List<String> ingredients, // массив айди ингредиентов
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order) {
        return ResponseEntity.ok(dishService.getFilteredDishes(principal.getName(), pageSize, currentPage, name, categories, ingredients, order));
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/stock")
    public ResponseEntity<PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>>> getDishesByStock(
            Principal principal,
            @RequestParam(value = "pageSize") @Min(value = 1, message = "Page size must be higher than 0") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int currentPage) {
        return ResponseEntity.ok(dishService.getDishesByStock(principal.getName(), pageSize, currentPage));
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping(value = "/like")
    public void updateLike(
            Principal principal,
            @RequestParam(value = "id", defaultValue = "0", required = false) long dishId,
            @RequestParam(value = "isLike", defaultValue = "", required = false) boolean isLike) {
        dishService.updateLike(principal.getName(), dishId, isLike);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping(value = "/favorite")
    public void updateFavorite(
            Principal principal,
            @RequestParam(value = "id", defaultValue = "0", required = false) long dishId,
            @RequestParam(value = "isFavorite", defaultValue = "", required = false) boolean isFavorite) {
        dishService.updateFavorite(principal.getName(), dishId, isFavorite);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/favorite")
    public ResponseEntity<PaginationDTO<DishInfoDTO<DishIngredientDTO, DishKitchenwareDTO>>> getFavoriteDishes(
            Principal principal,
            @RequestParam(value = "pageSize") @Min(value = 1, message = "Page size must be higher than 0") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int currentPage) {
        return ResponseEntity.ok(dishService.getFavoriteDishes(principal.getName(), pageSize, currentPage));
    }
}
