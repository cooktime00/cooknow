package com.side.cooknow.domain.ingredient.controller;


import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.domain.ingredient.model.dto.request.RequestGetIngredientsDto;
import com.side.cooknow.domain.ingredient.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.ingredient.model.dto.response.ResponseDeleteDto;
import com.side.cooknow.domain.ingredient.model.dto.response.ResponseGetIngredients;
import com.side.cooknow.domain.ingredient.model.dto.response.ResponseSaveDto;
import com.side.cooknow.domain.ingredient.service.IngredientService;
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
    public ResponseEntity<ResponseSaveDto> save(RequestSaveDto requestDto) throws IOException {
        Ingredient ingredient = ingredientService.save(requestDto);
        ResponseSaveDto responseDto = new ResponseSaveDto(ingredient);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<ResponseGetIngredients>> getIngredients(@RequestBody RequestGetIngredientsDto requestDto) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        Ingredients ingredients = ingredientService.getIngredients(requestDto.getIds());
        return new ResponseEntity<>(ingredients.toDtos(ResponseGetIngredients::new, requestLocale), HttpStatus.OK);
    }

    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<ResponseDeleteDto> delete(@PathVariable("id") Long ingredientId) {
        ingredientService.delete(ingredientId);
        return new ResponseEntity<>(new ResponseDeleteDto(ingredientId), HttpStatus.OK);
    }

}
