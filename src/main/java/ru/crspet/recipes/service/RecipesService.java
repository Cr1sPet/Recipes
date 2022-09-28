package ru.crspet.recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.crspet.recipes.entity.Recipe;
import ru.crspet.recipes.repository.RecipesRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipesService {

    private RecipesRepository repository;

    public RecipesService(RecipesRepository repository) {
        this.repository = repository;
    }

    public Map createRecipe(Recipe recipe) {
        repository.save(recipe);
        return Collections.singletonMap("id", recipe.getId());
    }

    public Recipe getRecipe(long id) throws ResponseStatusException {
        return getRecipeById(id);
    }

    public ResponseEntity removeRecipe(long id) throws ResponseStatusException {
        Recipe recipe = getRecipeById(id);
        repository.delete(recipe);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity updateRecipe(long id, Recipe recipe) throws ResponseStatusException {
        getRecipeById(id);
        repository.save(recipe);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Recipe getRecipeById(long id) throws ResponseStatusException {
        Optional<Recipe> optionalRecipe = repository.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe dont exists");
        }
        return optionalRecipe.get();
    }

    public List<Recipe> searchRecipes(String name, String category) {
        if (name != null && category != null ||
            name == null && category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number of arguments");
        }
        if (name != null) {
            return repository.findByNameContainsOrderByDateDesc(name);
        }
        return repository.findByCategoryOrderByDateDesc(category);
    }


    public List<Recipe> getAllRecipes() {
        return repository.findAllByOrderByDateDesc();
    }
}
