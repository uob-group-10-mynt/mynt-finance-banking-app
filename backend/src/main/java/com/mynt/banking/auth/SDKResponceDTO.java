package com.mynt.banking.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class SDKResponceDTO {
    String stage;
//    String data;
    HashMap<String,Object> data;

    public SDKResponceDTO() {
        data= new HashMap<>();
//        data= "";
    }

}
