package com.side.cooktime.domain.category.service;

import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.category.model.Ingredients;
import com.side.cooktime.domain.category.model.dto.response.ResponseFindIngredientsDto;
import com.side.cooktime.domain.category.repository.CategoryRepository;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public Ingredients getIngredients(Long id) {
        Category category = findById(id);
        return category.getIngredients();
    }

    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
