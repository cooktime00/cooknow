package com.side.cooknow.domain.ingredient.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class FindIngredientsResponse {
    private List<FindIngredientResponse> ingredientList;


}
