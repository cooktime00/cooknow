package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorages;
import lombok.Getter;

@Getter
public class ResponseSaveDto {

    private int size;

    public ResponseSaveDto(int size) {
        this.size = size;
    }
}
