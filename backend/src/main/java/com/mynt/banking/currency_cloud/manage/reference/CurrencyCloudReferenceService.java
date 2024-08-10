
package com.mynt.banking.currency_cloud.manage.reference;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.CurrencyCloudAuthenticator;
import com.mynt.banking.currency_cloud.config.CurrencyCloudClient;
import com.mynt.banking.currency_cloud.manage.reference.requests.*;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyCloudReferenceService {
    private final CurrencyCloudClient currencyCloudClient;
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    
    public ResponseEntity<JsonNode> findBankDetails(
            CurrencyCloudFindBankDetailsRequest request
    ) {
        String uri = "/v2/reference/bank_details/find";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getBeneficiaryRequirements(
            CurrencyCloudGetBeneficiaryRequirementsRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/beneficiary_required_details", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getConversionDates(
            CurrencyCloudGetConversionDatesRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/conversion_dates", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getAvailableCurrencies(
            
    ) {
        String uri = "/v2/reference/currencies";
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPayerRequirements(
            CurrencyCloudGetPayerRequirementsRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/payer_required_details", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentDates(
            CurrencyCloudGetPaymentDatesRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/payment_dates", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentFeeRules(
            CurrencyCloudGetPaymentFeeRulesRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/payment_fee_rules", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentPurposeCodes(
            CurrencyCloudGetPaymentPurposeCodesRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/payment_purpose_codes", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getSettlementAccounts(
            CurrencyCloudGetSettlementAccountsRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/settlement_accounts", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
}