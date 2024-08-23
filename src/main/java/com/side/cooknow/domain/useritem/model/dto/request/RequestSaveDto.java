package com.side.cooknow.domain.useritem.model.dto.request;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.StorageType;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Data
public class RequestSaveDto {
    private List<Item> itemList;

    @Data
    public static class Item{
        private Long ingredientId;     // Ingredient의 ID
        private int quantity;      // 수량
        private LocalDate expirationDate;  // 만료 날짜
        private String storageType;    // 저장 타입 (예: "COLD", "FROZEN", etc.)
    }
}
