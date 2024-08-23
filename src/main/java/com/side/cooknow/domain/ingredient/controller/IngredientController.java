package com.side.cooknow.domain.ingredient.controller;


import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.domain.ingredient.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.ingredient.model.dto.response.DeleteResponse;
import com.side.cooknow.domain.ingredient.model.dto.response.FindIngredientResponse;
import com.side.cooknow.domain.ingredient.model.dto.response.SaveResponse;
import com.side.cooknow.domain.ingredient.service.IngredientService;
import com.side.cooknow.global.model.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping(value = "/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SaveResponse>> save(RequestSaveDto requestDto) throws IOException {
        Ingredient ingredient = ingredientService.save(requestDto);
        SaveResponse responseDto = new SaveResponse(ingredient);
        return createResponse("Ingredient saved successfully", responseDto);
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<ApiResponse<FindIngredientResponse>> findIngredient(@PathVariable("id") Long ingredientId) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Ingredient ingredient = ingredientService.findById(ingredientId);
        return createResponse("Find ingredient successfully", new FindIngredientResponse(ingredient, requestLocale));
    }

    @GetMapping("/ingredients")
    public ResponseEntity<ApiResponse<List<FindIngredientResponse>>> findIngredients(@RequestParam List<Long> ids) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Ingredients ingredients = ingredientService.findByIds(ids);
        return createResponse("Find all ingredients successfully", ingredients.toDtos(FindIngredientResponse::new, requestLocale));
    }

    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<ApiResponse<DeleteResponse>> delete(@PathVariable("id") Long ingredientId) {
        ingredientService.delete(ingredientId);
        return createResponse("Ingredient deleted successfully", new DeleteResponse(ingredientId));
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponse(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK.value(), message, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
