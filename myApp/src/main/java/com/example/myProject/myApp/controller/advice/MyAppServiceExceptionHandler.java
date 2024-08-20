package com.example.myProject.myApp.controller.advice;

import com.example.myProject.myApp.exception.NoSuchProductCategoryException;
import com.example.myProject.myApp.exception.NoSuchProductException;
import com.example.myProject.myApp.exception.ProductAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MyAppServiceExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(NoSuchProductCategoryException.class)
    public ResponseEntity<String> handleNoSuchProductCategoryException(NoSuchProductCategoryException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<String> handleNoSuchProductException(NoSuchProductException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<String> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }


}
