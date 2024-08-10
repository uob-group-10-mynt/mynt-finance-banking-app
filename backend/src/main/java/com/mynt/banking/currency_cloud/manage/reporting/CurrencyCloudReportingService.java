
package com.mynt.banking.currency_cloud.manage.reporting;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.CurrencyCloudAuthenticator;
import com.mynt.banking.currency_cloud.config.CurrencyCloudClient;
import com.mynt.banking.currency_cloud.manage.reporting.requests.*;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyCloudReportingService {
    private final CurrencyCloudClient currencyCloudClient;
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    
    public ResponseEntity<JsonNode> generateConversionReport(
            CurrencyCloudGenerateConversionReportRequest request
    ) {
        String uri = "/v2/reports/conversions/create";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> generatePaymentReport(
            CurrencyCloudGeneratePaymentReportRequest request
    ) {
        String uri = "/v2/reports/payments/create";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> findReportRequests(
            CurrencyCloudFindReportRequestsRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reports/report_requests/find", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> retrievesAReportRequestWithTheGivenId(
            String id,
			CurrencyCloudRetrievesAReportRequestWithTheGivenIdRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reports/report_requests/" + id + "", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
}