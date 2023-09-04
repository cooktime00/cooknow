package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
public class ResponseGetNearExpiryDto {
    private final Long id;
    private final String ingredientName;
    private final String ingredientImageUrl;
    private final int quantity;
    private final int remainDays;
    private final String expireStatus;

    public ResponseGetNearExpiryDto(UserStorage userStorage) {
        this.id = userStorage.getId();

        Ingredient ingredient = userStorage.getIngredient();
        this.ingredientName = ingredient.getName();
        this.ingredientImageUrl = ingredient.getImageUrl();

        this.quantity = userStorage.getQuantity();

        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = userStorage.getExpirationDate();
        Period period = Period.between(currentDate, expirationDate);
        this.remainDays = period.getDays();
        if (period.isNegative()) {
            expireStatus = "EXPIRED";
        } else {
            expireStatus = "SOON";
        }
    }
}
