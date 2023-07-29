package com.side.cooktime.domain.category.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import lombok.Data;

@Data
public class ResponseFindIngredientsDto {

    private Long ingredientId;
    private Long categoryId;
    private String name;
    private String imageUrl;

    public ResponseFindIngredientsDto(Ingredient ingredient) {
        this.ingredientId = ingredient.getId();
        this.categoryId = ingredient.getCategory().getId();
        this.name = ingredient.getName().getName();
        this.imageUrl = ingredient.getImage().getUrl();
    }
}
