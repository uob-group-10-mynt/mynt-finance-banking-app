package com.mynt.banking.currency_cloud.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class Utils {

    public static <T> MultiValueMap<String, Object> buildFormData(T request) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = mapper.convertValue(request, new TypeReference<Map<String, Object>>() {});

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                formData.add(entry.getKey(), entry.getValue().toString());
            }
        }

        return formData;
    }
}
