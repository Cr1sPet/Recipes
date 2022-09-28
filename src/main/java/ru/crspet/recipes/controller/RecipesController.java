package ru.crspet.recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.crspet.recipes.entity.Recipe;
import ru.crspet.recipes.service.RecipesService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/recipes/")
public class RecipesController {

    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping("")
    public List<Recipe> getRecipes() {
        return recipesService.getAllRecipes();
    }

    @PostMapping("")
    public Map createRecipe(@Valid @RequestBody Recipe recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return recipesService.createRecipe(recipe);
    }

    @GetMapping("{id}")
    public Recipe getRecipe(@PathVariable long id) {
        return recipesService.getRecipe(id);
    }

    @PutMapping("{id}")
    public ResponseEntity updateRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
        return recipesService.updateRecipe(id, recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeRecipe(@PathVariable long id) {
        return recipesService.removeRecipe(id);
    }

    @GetMapping("search")
    public List<Recipe> searchRecipes(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        return recipesService.searchRecipes(name, category);
    }


}
