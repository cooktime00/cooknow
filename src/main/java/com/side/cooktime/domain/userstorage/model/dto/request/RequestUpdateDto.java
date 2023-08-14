package com.side.cooktime.domain.userstorage.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestUpdateDto {
    private List<RequestUpdateOneDto> request;
}
