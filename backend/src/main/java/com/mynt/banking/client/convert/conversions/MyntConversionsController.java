package com.mynt.banking.client.convert.conversions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.convert.conversions.requests.MyntCreateConversionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/conversions")
@RequiredArgsConstructor
public class MyntConversionsController {

    private final MyntConversionsService myntConversionsService;

    @PostMapping("/createConversion")
    public ResponseEntity<JsonNode> createConversion(@Valid @RequestBody MyntCreateConversionRequest request) {
        return myntConversionsService.createConversion(request);
    }
}
