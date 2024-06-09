package com.example.library.management.tool.library.util;

public class ValidatorUtil {

    public static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }
}
