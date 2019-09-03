package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3999, 1, 1);

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date.equals(NOW)) {
            return "now";
        } else {
            final DateTimeFormatter f = DateTimeFormatter.ofPattern("M/yyyy");
            return date.format(f);
        }
    }
}
