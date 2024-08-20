package com.example.myProject.myApp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductAlreadyExistsException extends RuntimeException {
    private String message;

    public ProductAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
