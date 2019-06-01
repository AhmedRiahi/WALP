package com.ef.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateHelper {


    private static final Map<String, DateTimeFormatter> FORMATTERS = new HashMap<>();

    static {
        FORMATTERS.put("yyyy-MM-dd HH:mm:ss.SSS", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        FORMATTERS.put("yyyy-MM-dd.HH:mm:ss", DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
    }

    public static LocalDateTime toLocalDateTime(String date, String format) {
        DateTimeFormatter dateTimeFormatter = DateHelper.FORMATTERS.computeIfAbsent(format,(x) -> DateTimeFormatter.ofPattern(format));
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
