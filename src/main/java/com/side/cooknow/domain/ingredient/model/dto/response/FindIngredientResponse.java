package com.side.cooknow.domain.ingredient.model.dto.response;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import lombok.Data;

import java.time.LocalDate;
import java.util.Locale;

@Data
public class FindIngredientResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private LocalDate expirationDate;
    private String type;
    private int count;

    public FindIngredientResponse(Ingredient ingredient, Locale locale) {
        this.id = ingredient.getId();
        this.type = ingredient.getStorageType().getName();
        this.count = 5;
        this.imageUrl = ingredient.getImageUrl();
        this.expirationDate = ingredient.getExpirationDate();
        this.name = ingredient.getName(locale);
    }
}
