package com.side.cooknow.domain.useritem.model.dto.response;

import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SaveResponse {

    private Long userId;
    private List<Item> itemList;

    @Data
    private static class Item {
        private Long id;
        private Long ingredientId;
        private int quantity;
        private LocalDate expirationDate;
        private String storageType;
    }

    public SaveResponse(Long userId, List<UserItem> userItems) {
        this.userId = userId;
        this.itemList = userItems.stream()
                .map(item -> {
                    Item itemDto = new Item();
                    itemDto.id = item.getId();
                    itemDto.ingredientId = item.getIngredient().getId();
                    itemDto.quantity = item.getQuantity();
                    itemDto.expirationDate = item.getExpirationDate();
                    itemDto.storageType = item.getStorageType().name();
                    return itemDto;
                })
                .toList();
    }
}
