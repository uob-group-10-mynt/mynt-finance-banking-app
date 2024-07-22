package com.mynt.banking.util;

import java.util.HashMap;

public class HashMapToQuiryPrams {

    public static String hashMapToString(HashMap<String,Object> map) {
        StringBuilder query = new StringBuilder();

        if(map == null || map.keySet().isEmpty()) {
            query.append("");
            return query.toString();
        }

        query.append("?");
        Boolean isStartOfQuiry = true;

        for (String key : map.keySet()) {
            String value = map.get(key).toString();
            if (value != null && value != "") {
                if (!isStartOfQuiry) {
                    query.append("&");
                }
                query.append(key + "=" + value);
                isStartOfQuiry = false;
            }
        }
        return query.toString();
    }
}
