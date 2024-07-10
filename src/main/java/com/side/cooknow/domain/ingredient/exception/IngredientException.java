package com.side.cooknow.domain.ingredient.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientException extends RuntimeException{
    IngredientErrorCode errorCode;
}
