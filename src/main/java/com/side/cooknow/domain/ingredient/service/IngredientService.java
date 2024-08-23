package com.side.cooknow.domain.ingredient.service;

import com.side.cooknow.domain.category.exception.CategoryErrorCode;
import com.side.cooknow.domain.category.exception.CategoryException;
import com.side.cooknow.domain.ingredient.exception.IngredientErrorCode;
import com.side.cooknow.domain.ingredient.exception.IngredientException;
import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.global.config.aws.AwsS3Service;
import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.category.service.CategoryService;
import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final CategoryService categoryService;
    private final AwsS3Service awsS3Service;

    public Ingredient save(RequestSaveDto requestDto) throws IOException {
        Category category = categoryService.findById(requestDto.getCategoryId());
        String s3ImageUrl = awsS3Service.uploadFile(requestDto.getUrl());
        Ingredient ingredient = requestDto.toEntity(category, s3ImageUrl);
        return ingredientRepository.save(ingredient);
    }

    @Transactional(readOnly = true)
    public Ingredient findById(Long id){
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientException(IngredientErrorCode.INGREDIENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Ingredients findByIds(List<Long> ids) {
        List<Ingredient> ingredients = ingredientRepository.findAllById(ids);
        return new Ingredients(ingredients);
    }

    public void delete(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }


}
