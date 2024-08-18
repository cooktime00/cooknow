package com.side.cooknow.domain.useritem.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestUpdateDto {
    private Long userId;
    private List<RequestUpdateOneDto> items;
}
