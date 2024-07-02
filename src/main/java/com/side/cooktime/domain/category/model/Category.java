package com.side.cooktime.domain.category.model;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.Ingredients;
import com.side.cooktime.domain.ingredient.model.Name;
import com.side.cooktime.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@SuperBuilder
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "kor_name", length = 100, nullable = false))
    private Name korName;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "eng_name", length = 100))
    private Name engName;

    @Embedded
    private final Ingredients ingredients = new Ingredients();

    public Category(final String korName) {
        this.korName = new Name(korName);
        this.engName = new Name("temp");
    }

    public Category(final Long id, final String korName) {
        super(id);
        this.korName = new Name(korName);
        this.engName = new Name("temp");
    }

    public Category(final Long id, final String korName, final String engName) {
        super(id);
        this.korName = new Name(korName);
        this.engName = new Name(engName);
    }

    public Category(final String korName, final String engName) {
        this();
        this.korName = new Name(korName);
        this.engName = new Name(engName);
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

    public String getKorName() {
        return korName.getName();
    }

    public String getEngName() {
        return engName.getName();
    }
}
