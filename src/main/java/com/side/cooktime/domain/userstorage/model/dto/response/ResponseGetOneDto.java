package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseGetOneDto {
    private Long id;
    private String ingredientName;
    private String ingredientImageUrl;
    private int quantity;
    private LocalDate expiration_date;
    private String storage_type;

    public ResponseGetOneDto(UserStorage userStorage) {
        this.id = userStorage.getId();

        Ingredient ingredient = userStorage.getIngredient();
        this.ingredientName = ingredient.getName().getName();  /*TODO:메서드체이닝 개선*/
        this.ingredientImageUrl = ingredient.getImage().getUrl();

        this.quantity = userStorage.getQuantity();
        this.expiration_date = userStorage.getExpirationDate();
        this.storage_type = userStorage.getStorageType().getName();
    }
}
