package com.side.cooknow.domain.ingredient.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;


class NameTest {

    @ParameterizedTest
    @NullAndEmptySource
    public void validateName(String name){
        assertThrows(IllegalArgumentException.class, () -> new Name(name), "이름이 공백이거나 Null 값입니다.");
    }

}