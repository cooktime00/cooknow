package com.side.cooktime.domain.ingredient.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import lombok.Data;

import java.time.LocalDate;
import java.util.Locale;

@Data
public class ResponseGetIngredients {

    private Long id;
    private String type;
    private int count;
    private String imageUrl;
    private LocalDate expirationDate;
    private String name;

    public ResponseGetIngredients(Ingredient ingredient, Locale locale){
        this.id = ingredient.getId();
        this.type = ingredient.getStorageType().getName();
        this.count = 5;
        this.imageUrl = ingredient.getImageUrl();
        this.expirationDate = ingredient.getExpirationDate();
        this.name = ingredient.getKorName();
    }

    private String getNameByLocale(Ingredient ingredient, Locale locale) {
        if (locale.getLanguage().equals("en")) {
            return ingredient.getEngName();
        }
        return ingredient.getKorName();
    }
}
