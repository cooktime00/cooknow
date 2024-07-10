package com.side.cooknow.domain.category.controller;


import com.side.cooknow.domain.category.model.Categories;
import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.category.model.dto.response.*;
import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.domain.category.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.category.service.CategoryService;
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
    public ResponseEntity<ResponseSaveDto> save(@RequestBody final RequestSaveDto requestDto) {
        Category category = categoryService.save(requestDto.toEntity());
        ResponseSaveDto responseDto = new ResponseSaveDto(1);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ResponseGetAllDto>> getAll() {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Categories categories = categoryService.getAll();
        return new ResponseEntity<>(categories.toDtos(ResponseGetAllDto::new, requestLocale), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/category/{id}/ingredients")
    public ResponseEntity<List<ResponseFindIngredientsDto>> findIngredients(@PathVariable("id") Long categoryId) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Ingredients ingredients = categoryService.getIngredients(categoryId);
        return new ResponseEntity<>(ingredients.toDtos(ResponseFindIngredientsDto::new, requestLocale), HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ResponseDeleteDto> delete(@PathVariable("id") Long categoryId) {
        categoryService.delete(categoryId);
        return new ResponseEntity<>(new ResponseDeleteDto(1), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/category/all/ingredients")
    public ResponseEntity<List<ResponseGetAllWithIngredientsDto>> getAllWithIngredients() {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Categories categories = categoryService.getAllWithIngredients();
        return new ResponseEntity<>(categories.toDtosResponseGetAllWithIngredientsDto(requestLocale), HttpStatus.OK);
    }
}
