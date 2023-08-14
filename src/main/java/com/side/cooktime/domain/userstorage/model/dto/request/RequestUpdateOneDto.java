package com.side.cooktime.domain.userstorage.model.dto.request;

import com.side.cooktime.domain.ingredient.model.StorageType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestUpdateOneDto {
    private Long id;
    private int quantity;
    private LocalDate expiration_date;
    private String storage_type;

    public StorageType getEnumStorageType() {
        if (StorageType.find(storage_type) == null) {
            //TODO: 예외처리 임시
            throw new IllegalArgumentException("잘못된 보관방식 값");
        }
        return StorageType.find(storage_type);
    }
}
