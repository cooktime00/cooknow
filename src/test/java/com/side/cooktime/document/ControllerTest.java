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
import com.side.cooktime.domain.user.repository.UserRepository;
import com.side.cooktime.domain.user.service.UserService;
import com.side.cooktime.domain.useritem.controller.UserItemController;
import com.side.cooktime.domain.useritem.repository.UserItemRepository;
import com.side.cooktime.domain.useritem.service.UserItemService;
import com.side.cooktime.global.FirebaseService;
import com.side.cooktime.global.config.auth.AuthenticationFilter;
import com.side.cooktime.global.controller.OAuthController;
import com.side.cooktime.global.repository.RefreshTokenRepository;
import com.side.cooktime.global.security.JwtTokenService;
import com.side.cooktime.global.service.RefreshTokenService;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@WebMvcTest({
        CategoryController.class,
        IngredientController.class,
        CommonDocController.class,
        UserItemController.class,
        OAuthController.class
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
    protected UserRepository userRepository;

    @MockBean
    protected UserItemRepository userItemRepository;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;

    @MockBean
    protected IngredientService ingredientService;

    @MockBean
    protected CategoryService categoryService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserItemService userItemService;

    @MockBean
    protected JwtTokenService jwtTokenService;

    @MockBean
    protected RefreshTokenService refreshTokenService;

    @MockBean
    protected FirebaseService firebaseService;

    @MockBean
    protected SecurityFilterChain devProtectedSecurityFilterChain;

    @MockBean
    protected AuthenticationFilter authenticationFilter;

    public String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
