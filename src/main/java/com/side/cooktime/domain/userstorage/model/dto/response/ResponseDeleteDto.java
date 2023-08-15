package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseDeleteDto {

    private int size;

    public ResponseDeleteDto(final int size) {
        this.size = size;
    }
}
