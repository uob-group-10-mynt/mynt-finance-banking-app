package com.mynt.banking.client.convert.conversions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.convert.conversions.requests.MyntCreateConversionRequest;
import com.mynt.banking.currency_cloud.convert.conversions.CurrencyCloudConversionsService;
import com.mynt.banking.currency_cloud.convert.conversions.requests.*;
import com.mynt.banking.user.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyntConversionsService {
    private final UserContextService userContextService;
    private final CurrencyCloudConversionsService conversionService;


    public ResponseEntity<JsonNode> createConversion(MyntCreateConversionRequest request) {
        String contactUuid = userContextService.getCurrentUserUuid();
        CurrencyCloudCreateConversionRequest createConversionRequest = CurrencyCloudCreateConversionRequest.builder()
                .onBehalfOf(contactUuid)
                .sellCurrency(request.getSellCurrency())
                .buyCurrency(request.getBuyCurrency())
                .fixedSide(request.getFixedSide())
                .amount(request.getAmount())
                .termAgreement(String.valueOf(true))
                .build();
        return conversionService.createConversion(createConversionRequest);
    }
}
