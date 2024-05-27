package com.example.library.management.tool.library.exceptions;

public class CustomLibraryException extends Exception {
    private int statusCode;

    public CustomLibraryException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}

