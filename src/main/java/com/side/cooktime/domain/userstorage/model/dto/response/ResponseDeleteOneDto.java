package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Data;

@Data
public class ResponseDeleteOneDto {
    private Long id;
    private String ingredient_name;

    public ResponseDeleteOneDto(UserStorage userStorage){
        this.id = userStorage.getId();
        this.ingredient_name = userStorage.getIngredient().getName().getName(); /*TODO:메서드체이닝 개선*/
    }
}
