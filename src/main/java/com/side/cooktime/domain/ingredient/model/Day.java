package com.side.cooktime.domain.ingredient.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class Day {

    @Column
    private int day;

    public Day(final int day) {
        validateDay(day);
        this.day = day;
    }

    private void validateDay(final int day) {
        if (day <= 0) {
            throw new IllegalArgumentException("올바른 날짜 값이 아닙니다.");
        }
    }
}
