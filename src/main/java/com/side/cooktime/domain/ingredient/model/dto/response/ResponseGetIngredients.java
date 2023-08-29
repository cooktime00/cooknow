package com.side.cooktime.domain.ingredient.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseGetIngredients {

    private Long id;
    private String type;
    private int count;
    private String imageUrl;
    private LocalDate expirationDate;
    private String name;

    public ResponseGetIngredients(Ingredient ingredient){
        this.id = ingredient.getId();
        this.type = ingredient.getStorageType().getName();
        this.count = 5;
        this.imageUrl = ingredient.getImageUrl();
        this.expirationDate = ingredient.getExpirationDate();
        this.name = ingredient.getName();
    }
}
