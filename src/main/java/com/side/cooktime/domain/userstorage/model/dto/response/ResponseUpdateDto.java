package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseUpdateDto {

    private final int size;

    public ResponseUpdateDto(final int size) {
        this.size = size;
    }
}
