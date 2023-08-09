package com.side.cooktime.domain.category.model;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@NoArgsConstructor
@Getter
@Embeddable
public class Ingredients {

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    public Ingredients(List<Ingredient> list) {
        ingredients = list;
    }

    public <T> List<T> toDtos(Function<Ingredient, T> mapper) {
        return ingredients.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    protected void remove(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    protected void add(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

}
