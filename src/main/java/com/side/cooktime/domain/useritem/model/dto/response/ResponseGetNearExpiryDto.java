package com.side.cooktime.domain.useritem.model.dto.response;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.useritem.model.UserItem;
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

    public ResponseGetNearExpiryDto(UserItem userItem) {
        this.id = userItem.getId();

        Ingredient ingredient = userItem.getIngredient();
        this.ingredientName = ingredient.getKorName();
        this.ingredientImageUrl = ingredient.getImageUrl();

        this.quantity = userItem.getQuantity();

        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = userItem.getExpirationDate();
        Period period = Period.between(currentDate, expirationDate);
        this.remainDays = period.getDays();
        if (period.isNegative()) {
            expireStatus = "EXPIRED";
        } else {
            expireStatus = "SOON";
        }
    }
}
