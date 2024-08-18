package com.side.cooknow.domain.useritem.model.dto.request;

import com.side.cooknow.domain.ingredient.model.StorageType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestUpdateOneDto {
    private Long id;
    private int quantity;
    private LocalDate expirationDate;
    private String storageType;

    public StorageType getEnumStorageType() {
        if (StorageType.find(storageType) == null) {
            //TODO: 예외처리 임시
            throw new IllegalArgumentException("잘못된 보관방식 값");
        }
        return StorageType.find(storageType);
    }
}
