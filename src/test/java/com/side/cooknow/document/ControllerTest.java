package com.side.cooknow.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.cooknow.CommonDocController;
import com.side.cooknow.domain.category.controller.CategoryController;
import com.side.cooknow.domain.category.repository.CategoryRepository;
import com.side.cooknow.domain.category.service.CategoryService;
import com.side.cooknow.domain.ingredient.controller.IngredientController;
import com.side.cooknow.domain.ingredient.repository.IngredientRepository;
import com.side.cooknow.domain.ingredient.service.IngredientService;
import com.side.cooknow.domain.user.repository.UserRepository;
import com.side.cooknow.domain.user.service.UserService;
import com.side.cooknow.domain.useritem.controller.UserItemController;
import com.side.cooknow.domain.useritem.repository.UserItemRepository;
import com.side.cooknow.domain.useritem.service.UserItemService;
import com.side.cooknow.global.FirebaseService;
import com.side.cooknow.global.config.auth.AuthenticationFacade;
import com.side.cooknow.global.controller.OAuthController;
import com.side.cooknow.global.repository.RefreshTokenRepository;
import com.side.cooknow.global.service.JwtTokenService;
import com.side.cooknow.global.service.RefreshTokenService;
import org.junit.jupiter.api.Disabled;
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
    protected AuthenticationFacade authenticationFacade;

    @MockBean
    protected SecurityFilterChain devProtectedSecurityFilterChain;

    public String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
