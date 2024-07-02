package com.side.cooktime.domain.ingredient.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void validateDay(final int day){
        assertThrows(IllegalArgumentException.class, () -> new Day(day), "올바른 날짜 값이 아닙니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20})
    public void calculateExpirationDate(final int day){
        Day nDay = new Day(day);

        assertEquals(nDay.calculateExpirationDate(), LocalDate.now().plusDays(day));
    }

}