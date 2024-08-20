package com.example.myProject.myApp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchProductException extends RuntimeException {
    private String message;

    public NoSuchProductException(String message) {
        super(message);
        this.message = message;
    }
}
