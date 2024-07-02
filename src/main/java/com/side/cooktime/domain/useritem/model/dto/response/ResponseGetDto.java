package com.side.cooktime.domain.useritem.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.useritem.model.UserItem;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ResponseGetDto {
    private Long id;
    private String ingredientName;
    private String ingredientImageUrl;
    private int quantity;
    private LocalDate expiration_date;
    private String storage_type;

    public ResponseGetDto(UserItem userItem) {
        this.id = userItem.getId();

        Ingredient ingredient = userItem.getIngredient();
        this.ingredientName = ingredient.getKorName();
        this.ingredientImageUrl = ingredient.getImageUrl();

        this.quantity = userItem.getQuantity();
        this.expiration_date = userItem.getExpirationDate();
        this.storage_type = userItem.getStorageType().getName();
    }
}
