package com.side.cooknow.global.exception;

import com.side.cooknow.domain.category.exception.CategoryException;
import com.side.cooknow.domain.ingredient.exception.IngredientException;
import com.side.cooknow.domain.user.exception.UserException;
import com.side.cooknow.domain.useritem.exception.UserItemException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserItemException.class)
    protected ResponseEntity<ErrorResponseEntity> handleUserItemException(UserItemException ex) {
        return ErrorResponseEntity.toResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(CategoryException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCategoryException(CategoryException ex) {
        log.info("CateogryException 발생");
        return ErrorResponseEntity.toResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(IngredientException.class)
    protected ResponseEntity<ErrorResponseEntity> handleIngredientException(IngredientException ex) {
        log.info("IngredientException 발생");
        return ErrorResponseEntity.toResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<ErrorResponseEntity> handleUserException(UserException ex) {
        log.info("UserException 발생");
        return ErrorResponseEntity.toResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(OauthException.class)
    protected ResponseEntity<ErrorResponseEntity> handleOauthException(OauthException ex) {
        log.info("OauthException 발생");
        return ErrorResponseEntity.toResponseEntity(ex.getErrorCode());
    }

//    @ExceptionHandler(FirebaseAuthException.class)
//    protected ResponseEntity<ErrorResponseEntity> handleUserException(FirebaseAuthException ex) {
//        log.info("FirebaseAuthException 발생");
//        AuthErrorCode errorCode = ex.getAuthErrorCode();
//        return ErrorResponseEntity.toResponseEntity(FirebaseAuthErrorCode.fromName(errorCode.name()));
//    }

}
