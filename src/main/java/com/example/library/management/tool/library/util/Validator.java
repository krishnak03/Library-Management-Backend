package com.example.library.management.tool.library.util;

public class Validator {

    public static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }
}
