package com.side.cooknow.domain.useritem.model.dto.response;

import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Locale;

@Data
public class ResponseFindDto {

    private Long id;
    private String name;
    private int quantity;
    private LocalDate expirationDate;
    private String storageType;
    private String url;

    public ResponseFindDto(UserItem userItem, Locale locale){
        this.id = userItem.getId();
        this.name = userItem.getIngredientName(locale);
        this.url = userItem.getIngredientImage();
        this.quantity = userItem.getQuantity();
        this.expirationDate = userItem.getExpirationDate();
        this.storageType = userItem.getStorageType().name();
    }
}
