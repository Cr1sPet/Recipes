package ru.crspet.recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.crspet.recipes.entity.Recipe;

import java.util.List;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameContainsOrderByDateDesc(String name);
    List<Recipe> findByCategoryOrderByDateDesc(String category);
    List<Recipe> findAllByOrderByDateDesc();
}
