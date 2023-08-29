package com.side.cooktime.domain.ingredient.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class RequestGetIngredientsDto {
    private List<RequestGetIngredientDto> ids;

    @NoArgsConstructor
    @Data
    public static class RequestGetIngredientDto{
        private Long id;
    }

    public List<Long> getIds(){
        return ids.stream()
                .map(RequestGetIngredientDto::getId)
                .collect(Collectors.toList());
    }
}
