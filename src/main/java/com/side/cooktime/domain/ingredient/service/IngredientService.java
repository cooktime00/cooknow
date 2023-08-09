package com.side.cooktime.domain.ingredient.service;

import com.side.cooktime.config.aws.S3UploadService;
import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.category.service.CategoryService;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}
