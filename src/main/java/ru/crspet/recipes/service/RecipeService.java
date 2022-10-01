package ru.crspet.recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import ru.crspet.recipes.model.User;
import ru.crspet.recipes.repository.RecipeRepository;
import ru.crspet.recipes.repository.UserRepository;
import ru.crspet.recipes.model.Recipe;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    public RecipeService
            (
            RecipeRepository recipeRepository,
            UserRepository userRepository
            ) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public Map saveRecipe(Recipe recipe, BindingResult bindingResult, UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findUserByEmail(userDetails.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found : " + userDetails.getUsername());
        }
        recipe.setAuthor(user);
        recipeRepository.save(recipe);

        return Collections.singletonMap("id", recipe.getId());
    }

    public Recipe getRecipeById(long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Recipe recipe = optionalRecipe.get();
        return recipe;
    }

    public ResponseEntity deleteRecipeById(long id, UserDetails userDetails) {


        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        Recipe recipe = optionalRecipe.get();
        if (optionalRecipe.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (!authorIsCurrentUser(userDetails.getUsername(), recipe)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        recipeRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity updateRecipe(long id, BindingResult bindingResult, Recipe recipe, UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            if (!authorIsCurrentUser(userDetails.getUsername(), optionalRecipe.get())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            recipe.setId(id);
            recipe.setAuthor(optionalRecipe.get().getAuthor());
            recipeRepository.save(recipe);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    private boolean authorIsCurrentUser(String username, Recipe recipe) {
        return  recipe.getAuthor() != null && username.equals(recipe.getAuthor().getEmail());
    }

    public List<Recipe> searchRecipe(String name, String category) {
        if (category != null && name != null || category == null && name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            List<Recipe> foundRecipes = null;
            if (category != null) {
                 foundRecipes = recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
            } else if (name != null) {
                foundRecipes = recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
            }
            return foundRecipes;
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllByOrderByDateDesc();
    }
}