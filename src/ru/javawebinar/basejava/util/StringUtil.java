package ru.javawebinar.basejava.util;

public class StringUtil {
    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
