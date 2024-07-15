package com.mynt.banking.currency_cloud.manage.reference;


import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.reference.requests.GetPayerRequirementsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/reference")
@RequiredArgsConstructor
public class ReferenceController {
    private final ReferenceService referenceService;

    @PostMapping("/getPayerRequirements")
    public Mono<ResponseEntity<JsonNode>> getPayerRequirements(@RequestBody GetPayerRequirementsRequest request) {
        return referenceService.getPayerRequirements(request);
    }
}
