package com.side.cooknow.global.exception;

import com.side.cooknow.domain.category.exception.CategoryException;
import com.side.cooknow.domain.ingredient.exception.IngredientException;
import com.side.cooknow.domain.user.exception.UserException;
import com.side.cooknow.domain.useritem.exception.UserItemException;
import com.side.cooknow.global.model.dto.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserItemException.class,
            CategoryException.class,
            IngredientException.class,
            UserException.class,
            OauthException.class
    })
    protected <E extends BaseCustomException> ResponseEntity<ApiResponse<Void>> handleCustomExceptions(E ex) {
        logException(ex);
        ApiResponse<Void> response = new ApiResponse<>(
                ex.getErrorCode().getHttpStatus().value(),
                ex.getErrorCode().getMessage(),
                null
        );
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(response);
    }

    private void logException(Exception ex) {
        log.info("{} 발생: {}", ex.getClass().getSimpleName(), ex.getMessage());
    }
}
