package com.example.guess_that_beast;

public class InvalidLevelException extends RuntimeException{
    public InvalidLevelException(String message) {
        super(message);
    }
}
