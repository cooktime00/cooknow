package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Data;

import java.util.List;

@Data
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
