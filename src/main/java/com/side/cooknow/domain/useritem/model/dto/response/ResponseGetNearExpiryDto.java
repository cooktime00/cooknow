package com.side.cooknow.domain.useritem.model.dto.response;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;

@Data
public class ResponseGetNearExpiryDto {
    private final Long id;
    private final String imageUrl;

    public ResponseGetNearExpiryDto(UserItem userItem) {
        this.id = userItem.getId();
        Ingredient ingredient = userItem.getIngredient();
        this.imageUrl = ingredient.getImageUrl();
    }
}
