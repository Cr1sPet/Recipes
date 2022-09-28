package ru.crspet.recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipe")

@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    private LocalDateTime date = LocalDateTime.now();

    @NotBlank
    private String description;

    @NotNull
    @ElementCollection
    private List<String> ingredients;

    @NotNull
    @ElementCollection
    private List<String> directions;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) { return true; }
        if (!(other instanceof Recipe)) { return false; }
        Recipe recipe = (Recipe) other;
        return recipe.id == id;
    }
}