package com.side.cooknow.domain.ingredient.service;

import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.global.config.aws.S3UploadService;
import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.category.service.CategoryService;
import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final CategoryService categoryService;
    private final S3UploadService s3UploadService;

    public Ingredient save(RequestSaveDto requestDto) throws IOException {
        Category category = categoryService.findById(requestDto.getCategoryId());
        String s3ImageUrl = s3UploadService.saveFile(requestDto.getUrl());
        Ingredient ingredient = requestDto.toEntity(category, s3ImageUrl);
        return ingredientRepository.save(ingredient);
    }

    public Ingredient findById(Long id){
        return ingredientRepository.findById(id).orElse(null);
    }

    public Ingredient getReferenceById(Long id){
        return ingredientRepository.getReferenceById(id);
    }

    public void delete(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    public Ingredients getIngredients(List<Long> ids) {
        List<Ingredient> ingredients = ingredientRepository.findAllById(ids);
        return new Ingredients(ingredients);
    }
}
