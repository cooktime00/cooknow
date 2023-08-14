package com.side.cooktime.domain.userstorage.model.dto.request;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.StorageType;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.userstorage.model.UserStorage;
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

    public UserStorage toEntity(Member member, Ingredient ingredient) {
        return UserStorage.builder()
                .member(member)
                .ingredient(ingredient)
                .quantity(quantity)
                .expirationDate(expiration_date)
                .storageType(StorageType.valueOf(storage_type))
                .build();
    }
}
