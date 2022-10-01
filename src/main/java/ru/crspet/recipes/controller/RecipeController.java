package ru.crspet.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.crspet.recipes.model.Recipe;
import ru.crspet.recipes.service.RecipeService;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/search/")
    public List<Recipe> searchRecipe(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {

        return recipeService.searchRecipe(name, category);

    }

    @PostMapping
    public Map postRecipe
            (
                    @Valid @RequestBody Recipe recipe,
                    @AuthenticationPrincipal UserDetails userDetails,
                    BindingResult bindingResult
            )
    {
        return recipeService.saveRecipe(recipe, bindingResult, userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRecipe
            (
                    @PathVariable long id,
                    @Valid @RequestBody Recipe recipe,
                    BindingResult bindingResult,
                    @AuthenticationPrincipal UserDetails userDetails
            ) {

        return recipeService.updateRecipe(id, bindingResult, recipe, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        return recipeService.deleteRecipeById(id, userDetails);
    }
}