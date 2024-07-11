package com.mynt.banking.util;

import java.util.HashMap;

public class HashMapToQuiryPrams {

    public static String hashMapToString(HashMap<String,Object> map) {
        StringBuilder quiry = new StringBuilder();
        quiry.append("?");
        Boolean isStartOfQuiry = true;
        for (String key : map.keySet()) {
            String value = map.get(key).toString();
            if (value != null && value != "") {
                if (!isStartOfQuiry) {
                    quiry.append("&");
                }
                quiry.append(key + "=" + value);
                isStartOfQuiry = false;
            }
        }
        return quiry.toString();
    }
}
