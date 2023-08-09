package com.side.cooktime.domain.userstorage.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestDeleteDto {
    private List<RequestDeleteOneDto> request;

    public List<Long> getIds() {
        return request.stream().map(RequestDeleteOneDto::getId).toList();
    }
}
