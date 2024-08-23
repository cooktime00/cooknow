package com.side.cooknow.domain.useritem.model.dto.response;

import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Data
public class FindNearExpiryResponse {

    private Long userId;
    private List<Item> itemList;

    @Data
    private static class Item {
        private Long id;
        private Long ingredientId;
        private String name;
        private int quantity;
        private LocalDate expirationDate;
        private String storageType;
        private String url;
    }

    public FindNearExpiryResponse(Long userId, List<UserItem> userItems, Locale locale) {
        this.userId = userId;
        this.itemList = userItems.stream()
                .map(item -> {
                    Item itemDto = new Item();
                    itemDto.id = item.getId();
                    itemDto.ingredientId = item.getIngredient().getId();
                    itemDto.name = item.getIngredient().getName(locale);
                    itemDto.quantity = item.getQuantity();
                    itemDto.expirationDate = item.getExpirationDate();
                    itemDto.storageType = item.getStorageType().name();
                    itemDto.url = item.getIngredient().getImageUrl();
                    return itemDto;
                })
                .toList();
    }
}
