package com.side.cooktime.domain.category.service;

import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.category.model.Ingredients;
import com.side.cooktime.domain.category.model.dto.response.ResponseFindIngredientsDto;
import com.side.cooktime.domain.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }

    public List<ResponseFindIngredientsDto> getIngredients(Long id){
        Category category = findById(id);
        Ingredients ingredients = category.getIngredients();
        return ingredients.toDtos(ResponseFindIngredientsDto::new);
    }

    public void delete(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
}
