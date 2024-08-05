package com.mynt.banking.client.convert.rates.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyntGetBasicRatesResponse {
    private final String currency;

    private final String rate;
}
