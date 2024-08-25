package com.side.cooknow.domain.ingredient.model;

import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Locale;

@NoArgsConstructor
@Getter
@SuperBuilder
@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "kor_name", length = 100, nullable = false))
    private Name korName;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "eng_name", length = 100))
    private Name engName;

    @Embedded
    private Image image;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "expiration_period"))
    private Day expirationPeriod;

    @Column
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Column
    @Enumerated(EnumType.STRING)
    private CountType countType;

    public Ingredient(final Long id) {
        this.id = id;
    }

    public Ingredient(final Long id, final String korName, final String image, final int expirationPeriod,final Category category) {
        super(id);
        this.korName = new Name(korName);
        this.engName = new Name("temp");
        this.image = new Image(image);
        this.expirationPeriod = new Day(expirationPeriod);
        this.storageType = StorageType.COLD;
        this.countType = CountType.AMOUNT;
        changeCategory(category);
    }

    public Ingredient(final Long id, final String korName, final String engName, final String image, final Category category) {
        super(id);
        this.korName = new Name(korName);
        this.engName = new Name(engName);
        this.image = new Image(image);
        changeCategory(category);
    }

    public Ingredient(final String korName, final String image, final int expirationPeriod, final StorageType storageType, final String categoryName, final CountType countType) {
        this();
        this.korName = new Name(korName);
        this.image = new Image(image);
        this.expirationPeriod = new Day(expirationPeriod);
        this.storageType = storageType;
        this.category = new Category(categoryName);
        this.countType = countType;
    }

    public void changeCategory(final Category category) {
        if (this.category != null) {
            this.category.removeIngredient(this);
        }
        this.category = category;
        category.addIngredient(this);
    }

    public String getName(Locale locale) {
        return locale.getLanguage().equals("en") ? engName.getName() : korName.getName();
    }

    public String getKorName() {
        return korName.getName();
    }

    public String getEngName() {
        return engName.getName();
    }

    public String getImageUrl() {
        return image.getUrl();
    }

    public int getExpirationPeriod() {
        return expirationPeriod.getDay();
    }

    public LocalDate getExpirationDate() {
        return expirationPeriod.calculateExpirationDate();
    }
}

