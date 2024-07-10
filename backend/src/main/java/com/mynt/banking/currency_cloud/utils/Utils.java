package com.mynt.banking.currency_cloud.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.CreateAccountRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class Utils {

    public static MultiValueMap<String, Object> buildFormData(CreateAccountRequest request) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = mapper.convertValue(request, new TypeReference<>() {});

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                formData.add(entry.getKey(), entry.getValue().toString());
            }
        }

        return formData;
    }

}
