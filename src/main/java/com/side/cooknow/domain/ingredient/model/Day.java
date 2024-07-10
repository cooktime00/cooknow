package com.side.cooknow.domain.ingredient.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Embeddable
public class Day {

    @Column
    private int day;

    public Day(final int day) {
        validateDay(day);
        this.day = day;
    }

    public LocalDate calculateExpirationDate(){
        return LocalDate.now().plusDays(day);
    }

    private void validateDay(final int day) {
        if (day <= 0) {
            throw new IllegalArgumentException("올바른 날짜 값이 아닙니다.");
        }
    }
}
