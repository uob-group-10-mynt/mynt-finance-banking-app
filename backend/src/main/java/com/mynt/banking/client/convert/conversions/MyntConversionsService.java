package com.mynt.banking.client.convert.conversions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.convert.conversions.requests.MyntCreateConversionRequest;
import com.mynt.banking.currency_cloud.convert.conversions.ConversionService;
import com.mynt.banking.currency_cloud.convert.conversions.CreateConversionRequest;
import com.mynt.banking.user.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyntConversionsService {
    private final UserContextService userContextService;
    private final ConversionService conversionService;


    public ResponseEntity<JsonNode> createConversion(MyntCreateConversionRequest request) {
        String contactUuid = userContextService.getCurrentUserUuid();
        CreateConversionRequest createConversionRequest = CreateConversionRequest.builder()
                .onBehalfOf(contactUuid)
                .sellCurrency(request.getSellCurrency())
                .buyCurrency(request.getBuyCurrency())
                .fixedSide(request.getFixedSide())
                .amount(request.getAmount())
                .termAgreement(true)
                .build();
        return conversionService.createConversion(createConversionRequest);
    }
}
