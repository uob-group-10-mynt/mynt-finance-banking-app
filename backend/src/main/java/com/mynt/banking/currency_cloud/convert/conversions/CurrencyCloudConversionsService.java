
package com.mynt.banking.currency_cloud.convert.conversions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.CurrencyCloudAuthenticator;
import com.mynt.banking.currency_cloud.config.CurrencyCloudClient;
import com.mynt.banking.currency_cloud.convert.conversions.requests.*;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyCloudConversionsService {
    private final CurrencyCloudClient currencyCloudClient;
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    
    public ResponseEntity<JsonNode> createConversion(
            CurrencyCloudCreateConversionRequest request
    ) {
        String uri = "/v2/conversions/create";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getConversion(
            String id,
			CurrencyCloudGetConversionRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/conversions/" + id + "", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> findConversions(
            CurrencyCloudFindConversionsRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/conversions/find", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> quoteConversionCancellation(
            String id
    ) {
        String uri = "/v2/conversions/" + id + "/cancellation_quote";
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> cancelAConversion(
            String id,
			CurrencyCloudCancelAConversionRequest request
    ) {
        String uri = "/v2/conversions/" + id + "/cancel";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> quoteConversionDateChange(
            String id,
			CurrencyCloudQuoteConversionDateChangeRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/conversions/" + id + "/date_change_quote", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> conversionDateChange(
            String id,
			CurrencyCloudConversionDateChangeRequest request
    ) {
        String uri = "/v2/conversions/" + id + "/date_change";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> retrieveProfitLoss(
            CurrencyCloudRetrieveProfitLossRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/conversions/profit_and_loss", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> conversionSplit(
            String id,
			CurrencyCloudConversionSplitRequest request
    ) {
        String uri = "/v2/conversions/" + id + "/split";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> conversionSplitHistory(
            String id
    ) {
        String uri = "/v2/conversions/" + id + "/split_history";
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
}