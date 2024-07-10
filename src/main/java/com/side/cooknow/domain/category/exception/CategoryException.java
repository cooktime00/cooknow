package com.side.cooknow.domain.category.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryException extends RuntimeException{
    CategoryErrorCode errorCode;
}
