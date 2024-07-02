package com.side.cooktime.domain.ingredient.model;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
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

    public <T> List<T> toDtos(BiFunction<Ingredient, Locale, T> mapper, Locale locale) {
        return ingredients.stream()
                .map(ingredient -> mapper.apply(ingredient, locale))
                .collect(Collectors.toList());
    }

    public void remove(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public void add(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

}
