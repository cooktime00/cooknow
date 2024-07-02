package com.side.cooktime.domain.useritem.model.dto.request;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.StorageType;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.domain.useritem.model.UserItem;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestSaveOneDto {
    private Long ingredient_id;
    private int quantity;
    private LocalDate expiration_date;
    private String storage_type;

    public void setStorage_type(String storage_type) {
        if (StorageType.find(storage_type) == null) {
            //TODO: 예외처리 임시
            throw new IllegalArgumentException("잘못된 보관방식 값");
        }
        this.storage_type = storage_type;
    }

    public UserItem toEntity(User user, Ingredient ingredient) {
        return UserItem.builder()
                .user(user)
                .ingredient(ingredient)
                .quantity(quantity)
                .expirationDate(expiration_date)
                .storageType(StorageType.valueOf(storage_type))
                .build();
    }
}
