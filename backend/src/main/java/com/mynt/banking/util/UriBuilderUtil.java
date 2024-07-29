package com.mynt.banking.util;

import org.springframework.web.util.UriComponentsBuilder;
import java.lang.reflect.Field;

public class UriBuilderUtil {

    public static String buildUriWithQueryParams(String path, Object dto) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(path);

        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                if (value != null && !value.toString().isEmpty()) {
                    uriBuilder.queryParam(convertToSnakeCase(field.getName()), value);
                }
            } catch (IllegalAccessException e) {
                // Handle the exception according to your needs
                e.printStackTrace();
            }
        }

        return uriBuilder.build().toUriString();
    }

    private static String convertToSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
