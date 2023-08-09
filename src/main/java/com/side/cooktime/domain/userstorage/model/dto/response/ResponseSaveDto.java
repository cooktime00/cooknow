package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseSaveDto {
    private final String memberEmail;
    private final List<ResponseSaveOneDto> responseSaveDtos;

    public ResponseSaveDto(String memberEmail, List<UserStorage> savedUserStorages) {
        this.memberEmail = memberEmail;
        this.responseSaveDtos = savedUserStorages.stream()
                .map(ResponseSaveOneDto::new)
                .toList();
    }
}
