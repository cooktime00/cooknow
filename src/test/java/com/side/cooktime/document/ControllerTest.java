package com.side.cooktime.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.cooktime.CommonDocController;
import com.side.cooktime.domain.category.controller.CategoryController;
import com.side.cooktime.domain.category.repository.CategoryRepository;
import com.side.cooktime.domain.category.service.CategoryService;
import com.side.cooktime.domain.ingredient.controller.IngredientController;
import com.side.cooktime.domain.ingredient.repository.IngredientRepository;
import com.side.cooktime.domain.ingredient.service.IngredientService;
import com.side.cooktime.domain.userstorage.controller.UserStorageController;
import com.side.cooktime.domain.userstorage.repository.UserStorageRepository;
import com.side.cooktime.domain.userstorage.service.UserStorageService;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@WebMvcTest({
        CategoryController.class,
        IngredientController.class,
        CommonDocController.class,
        UserStorageController.class
})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerTest {

    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    public MockMvc mockMvc;

    @MockBean
    protected CategoryRepository categoryRepository;

    @MockBean
    protected IngredientRepository ingredientRepository;

    @MockBean
    protected UserStorageRepository userStorageRepository;

    @MockBean
    protected IngredientService ingredientService;

    @MockBean
    protected CategoryService categoryService;

    @MockBean
    protected UserStorageService userStorageService;

    public String createJson(Object dto) throws JsonProcessingException{
        return objectMapper.writeValueAsString(dto);
    }
}
