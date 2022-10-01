package ru.crspet.recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.crspet.recipes.model.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name);

    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findAllByOrderByDateDesc();
}
