package com.side.cooktime.domain.category.service;

import com.side.cooktime.domain.category.exception.CategoryErrorCode;
import com.side.cooktime.domain.category.exception.CategoryException;
import com.side.cooktime.domain.category.model.Categories;
import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.ingredient.model.Ingredients;
import com.side.cooktime.domain.category.repository.CategoryRepository;
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
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryException(CategoryErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public Ingredients getIngredients(Long id) {
        Category category = findById(id);
        return category.getIngredients();
    }

    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Categories getAll() {
        List<Category> categories = categoryRepository.findAll();
        return new Categories(categories);
    }

    public Categories getAllWithIngredients() {
        List<Category> categories = categoryRepository.findAllWithIngredients();
        return new Categories(categories);
    }
}
