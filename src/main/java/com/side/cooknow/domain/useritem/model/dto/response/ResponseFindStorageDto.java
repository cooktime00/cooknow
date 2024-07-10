package com.side.cooknow.domain.useritem.model.dto.response;

import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;

@Data
public class ResponseFindStorageDto {

    private Long id;
    private String name;
    private String url;

    public ResponseFindStorageDto(UserItem userItem){
        this.id = userItem.getId();
        this.name = userItem.getIngredientName();
        this.url = userItem.getIngredientImage();
    }
}
