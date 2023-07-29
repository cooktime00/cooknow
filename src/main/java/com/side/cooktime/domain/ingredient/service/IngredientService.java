package com.side.cooktime.domain.ingredient.service;

import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.category.service.CategoryService;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final CategoryService categoryService;

    public Ingredient save(RequestSaveDto requestDto) {
        Category category = categoryService.findById(requestDto.getCategoryId());
        Ingredient ingredient = requestDto.toEntity(category);
        return ingredientRepository.save(ingredient);
    }

    public void delete(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }
}
