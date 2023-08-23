package com.side.cooktime.domain.category.model;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.Name;
import com.side.cooktime.global.model.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@SuperBuilder
@Entity
public class Category extends BaseEntity {

    @Embedded
    private Name name;

    @Embedded
    private final Ingredients ingredients = new Ingredients();

    public Category(final Long id, final String name){
        super(id);
        this.name = new Name(name);
    }

    public Category(final String name) {
        this();
        this.name = new Name(name);
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }
}
