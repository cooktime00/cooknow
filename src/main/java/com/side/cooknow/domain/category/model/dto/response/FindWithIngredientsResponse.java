package com.side.cooknow.domain.category.model.dto.response;

import com.side.cooknow.domain.category.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@Data
public class FindWithIngredientsResponse {

    private List<CategoryDto> categoryList;

    public FindWithIngredientsResponse(List<Category> categoryList, Locale locale) {
        this.categoryList = categoryList.stream()
                .map(category -> new CategoryDto(category, locale))
                .toList();
    }

    @NoArgsConstructor
    @Data
    private static class CategoryDto {
        private Long id;
        private String name;
        private List<IngredientDto> ingredientList;

        public CategoryDto(Category category, Locale locale) {
            this.id = category.getId();
            this.name = category.getName(locale);
            this.ingredientList = category.getIngredients().getIngredients().stream()
                    .map(ingredient -> new IngredientDto(ingredient.getId(), ingredient.getName(locale), ingredient.getImageUrl(), ingredient.getExpirationPeriod()))
                    .toList();
        }
    }

    @NoArgsConstructor
    @Data
    private static class IngredientDto {
        private Long id;
        private String name;
        private String imageUrl;
        private int expirationDate;

        public IngredientDto(Long id, String name, String imageUrl, int expirationPeriod) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.expirationDate = expirationPeriod;
        }
    }

}
