
package com.mynt.banking.currency_cloud.pay.beneficiaries;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.CurrencyCloudAuthenticator;
import com.mynt.banking.currency_cloud.config.CurrencyCloudClient;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.*;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyCloudBeneficiariesService {
    private final CurrencyCloudClient currencyCloudClient;
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    
    public ResponseEntity<JsonNode> findBeneficiaries(
            CurrencyCloudFindBeneficiariesRequest request
    ) {
        String uri = "/v2/beneficiaries/find";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getBeneficiary(
            String id,
			CurrencyCloudGetBeneficiaryRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/beneficiaries/" + id + "", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> updateBeneficiary(
            String id,
			CurrencyCloudUpdateBeneficiaryRequest request
    ) {
        String uri = "/v2/beneficiaries/" + id + "";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> createBeneficiary(
            CurrencyCloudCreateBeneficiaryRequest request
    ) {
        String uri = "/v2/beneficiaries/create";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> deleteBeneficiary(
            String id,
			CurrencyCloudDeleteBeneficiaryRequest request
    ) {
        String uri = "/v2/beneficiaries/" + id + "/delete";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> validateBeneficiary(
            CurrencyCloudValidateBeneficiaryRequest request
    ) {
        String uri = "/v2/beneficiaries/validate";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> verifyBeneficiaryAccount(
            CurrencyCloudVerifyBeneficiaryAccountRequest request
    ) {
        String uri = "/v2/beneficiaries/account_verification";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
}