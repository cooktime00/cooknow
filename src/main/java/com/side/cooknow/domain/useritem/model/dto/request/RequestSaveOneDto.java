package com.side.cooknow.domain.useritem.model.dto.request;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.StorageType;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestSaveOneDto {
    private Long id;
    private int quantity;
    private LocalDate expirationDate;
    private String storageType;

    public void setStorageType(String storage_type) {
        if (StorageType.find(storage_type) == null) {
            //TODO: 예외처리 임시
            throw new IllegalArgumentException("잘못된 보관방식 값");
        }
        this.storageType = storage_type;
    }

    public UserItem toEntity(User user, Ingredient ingredient) {
        return UserItem.builder()
                .user(user)
                .ingredient(ingredient)
                .quantity(quantity)
                .expirationDate(expirationDate)
                .storageType(StorageType.valueOf(storageType))
                .build();
    }
}
