package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ResponseGetDto {
    private Long id;
    private String ingredientName;
    private String ingredientImageUrl;
    private int quantity;
    private LocalDate expiration_date;
    private String storage_type;

    public ResponseGetDto(UserStorage userStorage) {
        this.id = userStorage.getId();

        Ingredient ingredient = userStorage.getIngredient();
        this.ingredientName = ingredient.getName();
        this.ingredientImageUrl = ingredient.getImageUrl();

        this.quantity = userStorage.getQuantity();
        this.expiration_date = userStorage.getExpirationDate();
        this.storage_type = userStorage.getStorageType().getName();
    }
}
