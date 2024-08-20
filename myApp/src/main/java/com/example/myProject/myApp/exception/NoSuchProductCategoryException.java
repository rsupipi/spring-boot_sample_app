package com.example.myProject.myApp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchProductCategoryException extends RuntimeException {
    private String message;

    public NoSuchProductCategoryException(String message) {
        super(message);
        this.message = message;
    }
}
