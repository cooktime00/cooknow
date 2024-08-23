package com.side.cooknow.domain.category.service;

import com.side.cooknow.domain.category.exception.CategoryErrorCode;
import com.side.cooknow.domain.category.exception.CategoryException;
import com.side.cooknow.domain.category.model.Categories;
import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Categories findAll() {
        List<Category> categories = categoryRepository.findAll();
        return new Categories(categories);
    }

    @Transactional(readOnly = true)
    public Category findOneIngredients(Long id) {
        Category category = findById(id);
        return category;
    }

    @Transactional(readOnly = true)
    public List<Category> findAllWithIngredients() {
        return categoryRepository.findAllWithIngredients();
    }

    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
