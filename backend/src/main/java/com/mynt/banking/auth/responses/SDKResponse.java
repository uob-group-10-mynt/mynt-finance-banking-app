package com.mynt.banking.auth.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SDKResponse {
    String stage;
    String data;
//    HashMap<String,Object> data;

    public SDKResponse() {
//        data = new HashMap<>();
        data= "";
    }

}