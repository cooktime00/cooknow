package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDeleteDto {
    private String memberEmail;
    private List<ResponseDeleteOneDto> responseDeleteDtos;

    public ResponseDeleteDto(String memberEmail, List<UserStorage> userStorages) {
        this.memberEmail = memberEmail;
        this.responseDeleteDtos = userStorages.stream()
                .map(ResponseDeleteOneDto::new)
                .toList();
    }
}
