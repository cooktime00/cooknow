package com.side.cooknow.domain.category.model.dto.response;

import com.side.cooknow.domain.category.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@Data
public class FindOneWithIngredients {

    private Long id;
    private String name;
    private List<IngredientDto> ingredientList;

    public FindOneWithIngredients(Category category, Locale locale) {
        this.id = category.getId();
        this.name = category.getName(locale);
        this.ingredientList = category.getIngredients().getIngredients().stream()
                .map(ingredient -> new IngredientDto(ingredient.getId(), ingredient.getName(locale), ingredient.getImageUrl()))
                .toList();
    }

    @NoArgsConstructor
    @Data
    private static class IngredientDto {
        private Long id;
        private String name;
        private String imageUrl;

        public IngredientDto(Long id, String name, String imageUrl) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
        }
    }
}
