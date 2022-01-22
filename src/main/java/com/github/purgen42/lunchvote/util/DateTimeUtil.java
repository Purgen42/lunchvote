package com.github.purgen42.lunchvote.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalTime now() {
        return LocalTime.now();
    }
}
