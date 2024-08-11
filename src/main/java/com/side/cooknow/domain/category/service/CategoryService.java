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
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryException(CategoryErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Ingredients getIngredients(Long id) {
        Category category = findById(id);
        return category.getIngredients();
    }

    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional(readOnly = true)
    public Categories getAll() {
        List<Category> categories = categoryRepository.findAll();
        return new Categories(categories);
    }

    @Transactional(readOnly = true)
    public Categories getAllWithIngredients() {
        List<Category> categories = categoryRepository.findAllWithIngredients();
        return new Categories(categories);
    }
}
