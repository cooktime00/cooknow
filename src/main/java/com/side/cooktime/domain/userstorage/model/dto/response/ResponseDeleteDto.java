package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseDeleteDto {
    private String memberEmail;
    private List<ResponseDeleteOneDto> response;

    public ResponseDeleteDto(String memberEmail, List<UserStorage> userStorages) {
        this.memberEmail = memberEmail;
        this.response = userStorages.stream()
                .map(ResponseDeleteOneDto::new)
                .toList();
    }
}
