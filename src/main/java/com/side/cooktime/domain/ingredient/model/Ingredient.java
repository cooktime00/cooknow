package com.side.cooktime.domain.ingredient.model;

import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@SuperBuilder
@Entity
public class Ingredient extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Embedded
    private Name name;
    @Embedded
    private Image image;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "expiration_Period"))
    private Day expirationPeriod;

    @Column
    @Enumerated(EnumType.STRING)
    private Storage storage;

    @Column
    @Enumerated(EnumType.STRING)
    private CountType countType;

    public Ingredient(final String name, final String image, final int expirationPeriod, final Storage storage, final String categoryName, final CountType countType) {
        this();
        this.name = new Name(name);
        this.image = new Image(image);
        this.expirationPeriod = new Day(expirationPeriod);
        this.storage = storage;
        this.category = new Category(categoryName);
        this.countType = countType;
    }

    public void changeCategory(Category category) {
        if (this.category != null) {
            this.category.removeIngredient(this);
        }
        this.category = category;
        category.addIngredient(this);
    }
}
