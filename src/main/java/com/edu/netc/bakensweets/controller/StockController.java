package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.dto.StockIngredientDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.form.SearchIngredientModel;
import com.edu.netc.bakensweets.model.form.SearchStockIngredientModel;
import com.edu.netc.bakensweets.service.IngredientServiceImpl;
import com.edu.netc.bakensweets.service.interfaces.IngredientService;
import com.edu.netc.bakensweets.service.interfaces.StockService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/stock")
@AllArgsConstructor
public class StockController {
    private final StockService stockService;
    private final IngredientService ingredientService;


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ingredient added to stock"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void addIngredient(@RequestParam(value = "ingredientId") long ingredientId,
                              @RequestParam(value = "amount",
                                      defaultValue = "10000", required = false) @Min(value = 1, message = "amount must be > 0")
                              @Max(value = 10000, message = "amount must be < 10000") int amount, Principal principal) {
        stockService.addToStock(principal.getName(), ingredientId, amount);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of ingredients to add to stock"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public PaginationDTO<Ingredient> getIngredients(@RequestParam(value = "size") int size,
                                                    @RequestParam(value = "pageNum", defaultValue = "0", required = false) int currentPage,
                                                    @RequestParam(value = "search", defaultValue = "", required = false) String search,
                                                    @RequestParam(value = "order", defaultValue = "true", required = false) boolean order,
                                                    @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
                                                    @RequestParam(value = "ingredientCategory", defaultValue = "", required = false) List<String> ingredientCategory,
                                                    Principal principal) {
        return stockService.getIngredientsToAdd(size, currentPage, search,  order, sortBy, ingredientCategory, principal.getName());
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cancelled adding ingredient"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void cancelAddingIngredient(@RequestParam(value = "ingredientId") long ingredientId,
                                       Principal principal) {
        stockService.deleteFromStock(principal.getName(), ingredientId);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/{userId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of stock changed"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void changeConfigurationIngredientFromStock(@PathVariable long userId, @RequestParam(value = "ingredientId") long ingredientId,
                                                       @RequestParam(value = "amount") @Min(value = 1, message = "amount must be > 0")
                                                       @Max(value = 10000, message = "amount must be < 10000") int amount) {
        stockService.updateIngredientFromStock(userId, ingredientId, amount);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @DeleteMapping(value = "/{userId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of stock changed"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public void deleteIngredientFromStock(@PathVariable long userId, @RequestParam(value = "ingredientId") long ingredientId) {
        stockService.deleteFromStock(userId, ingredientId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Your stock"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public PaginationDTO<StockIngredientDTO> getStock(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "ingredientCategory", defaultValue = "", required = false) List<String> ingredientCategory,
            Principal principal) {
        return stockService.getIngredientsFromStock(size, currentPage, search,  order, sortBy, ingredientCategory, principal.getName());
    }
}
