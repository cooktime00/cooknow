package com.side.cooktime.domain.userstorage.model.dto.response;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseUpdateDto {
    private final String memberEmail;
    private final List<ResponseUpdateOneDto> response;

    public ResponseUpdateDto(String memberEmail, List<UserStorage> savedUserStorages) {
        this.memberEmail = memberEmail;
        this.response = savedUserStorages.stream()
                .map(ResponseUpdateOneDto::new)
                .toList();
    }
}
