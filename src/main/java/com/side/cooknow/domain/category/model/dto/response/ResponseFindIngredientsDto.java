package com.side.cooknow.domain.category.model.dto.response;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import lombok.Data;

import java.util.Locale;

@Data
public class ResponseFindIngredientsDto {

    private Long ingredientId;
    private Long categoryId;
    private String name;
    private String imageUrl;

    public ResponseFindIngredientsDto(Ingredient ingredient, Locale locale) {
        this.ingredientId = ingredient.getId();
        this.categoryId = ingredient.getCategory().getId();
        this.name = getNameByLocale(ingredient, locale);
        this.imageUrl = ingredient.getImage().getUrl();
    }

    private String getNameByLocale(Ingredient ingredient, Locale locale) {
        if (locale.getLanguage().equals("en")) {
            return ingredient.getEngName();
        }
        return ingredient.getKorName();
    }
}
