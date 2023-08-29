package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Data;

@Data
public class ResponseFindStorageDto {

    private Long id;
    private String name;
    private String url;

    public ResponseFindStorageDto(UserStorage userStorage){
        this.id = userStorage.getId();
        this.name = userStorage.getIngredientName();
        this.url = userStorage.getIngredientImage();
    }
}
