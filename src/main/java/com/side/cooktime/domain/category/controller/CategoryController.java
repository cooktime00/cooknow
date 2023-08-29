package com.side.cooktime.domain.category.controller;


import com.side.cooktime.domain.category.model.Categories;
import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.ingredient.model.Ingredients;
import com.side.cooktime.domain.category.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.category.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.category.model.dto.response.ResponseFindIngredientsDto;
import com.side.cooktime.domain.category.model.dto.response.ResponseGetAllDto;
import com.side.cooktime.domain.category.model.dto.response.ResponseSaveDto;
import com.side.cooktime.domain.category.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ResponseGetAllDto>> getAll(){
        Categories categories = categoryService.getAll();
        return new ResponseEntity<>(categories.toDtos(ResponseGetAllDto::new), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/category/{id}/ingredients")
    public ResponseEntity<List<ResponseFindIngredientsDto>> findIngredients(@PathVariable("id") Long categoryId) {
        Ingredients ingredients = categoryService.getIngredients(categoryId);
        return new ResponseEntity<>(ingredients.toDtos(ResponseFindIngredientsDto::new), HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ResponseDeleteDto> delete(@PathVariable("id") Long categoryId) {
        categoryService.delete(categoryId);
        return new ResponseEntity<>(new ResponseDeleteDto(1), HttpStatus.OK);
    }
}
