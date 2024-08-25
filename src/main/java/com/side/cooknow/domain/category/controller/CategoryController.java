package com.side.cooknow.domain.category.controller;


import com.side.cooknow.domain.category.model.Categories;
import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.category.model.dto.response.*;
import com.side.cooknow.domain.category.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.category.service.CategoryService;
import com.side.cooknow.global.model.dto.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/category")
    public ResponseEntity<ApiResponse<SaveResponse>> save(@RequestBody final RequestSaveDto requestDto) {
        Category category = categoryService.save(requestDto.toEntity());
        SaveResponse response = new SaveResponse(category);
        return createResponse("Category saved successfully", response);
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<FindAllResponse>>> findAll() {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Categories categories = categoryService.findAll();
        return createResponse("Find all categories successfully", categories.toDtos(FindAllResponse::new, requestLocale));
    }

    @Transactional
    @GetMapping("/category/{id}/ingredients")
    public ResponseEntity<ApiResponse<FindOneWithIngredients>> findWithIngredients(@PathVariable("id") Long categoryId) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Category category = categoryService.findOneIngredients(categoryId);
        return createResponse("Find category with ingredients successfully", new FindOneWithIngredients(category, requestLocale));
    }

    @Transactional
    @GetMapping("/categories/ingredients")
    public ResponseEntity<ApiResponse<FindWithIngredientsResponse>> findAllWithIngredients() {
        Locale requestLocale = LocaleContextHolder.getLocale();
        List<Category> categoryList = categoryService.findAllWithIngredients();
        return createResponse("Find categoryList with ingredients successfully", new FindWithIngredientsResponse(categoryList, requestLocale));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ApiResponse<DeleteResponse>> delete(@PathVariable("id") Long categoryId) {
        categoryService.delete(categoryId);
        return createResponse("Category deleted successfully", new DeleteResponse(1));
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponse(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK.value(), message, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
