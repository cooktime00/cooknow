package com.side.cooknow.domain.useritem.model.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestUpdateOneDto {
    private Long id;
    private int quantity;
    private String storageType;
    private LocalDate expirationDate;
}
