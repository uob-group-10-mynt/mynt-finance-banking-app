package com.mynt.banking.util.exceptions.currency_cloud;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CurrencyCloudException extends RuntimeException {

    private final HttpStatusCode status;

    public CurrencyCloudException(String message, HttpStatusCode status) {
        super(message);
        this.status = status;
    }
}
