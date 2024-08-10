
package com.mynt.banking.currency_cloud.pay.payments;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.CurrencyCloudAuthenticator;
import com.mynt.banking.currency_cloud.config.CurrencyCloudClient;
import com.mynt.banking.currency_cloud.pay.payments.requests.*;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyCloudPaymentsService {
    private final CurrencyCloudClient currencyCloudClient;
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    
    public ResponseEntity<JsonNode> createPayment(
            CurrencyCloudCreatePaymentRequest request
    ) {
        String uri = "/v2/payments/create";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> findPayments(
            CurrencyCloudFindPaymentsRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/payments/find", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPayment(
            String id,
			CurrencyCloudGetPaymentRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/payments/" + id + "", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> updatePayment(
            String id,
			CurrencyCloudUpdatePaymentRequest request
    ) {
        String uri = "/v2/payments/" + id + "";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentConfirmation(
            String id
    ) {
        String uri = "/v2/payments/" + id + "/confirmation";
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentSubmission(
            String id,
			CurrencyCloudGetPaymentSubmissionRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/payments/" + id + "/submission", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> authorisePayment(
            CurrencyCloudAuthorisePaymentRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/payments/authorise", request);
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> deletePayment(
            String id,
			CurrencyCloudDeletePaymentRequest request
    ) {
        String uri = "/v2/payments/" + id + "/delete";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentTrackingInformation(
            String id
    ) {
        String uri = "/v2/payments/" + id + "/tracking_info";
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> assignPaymentFee(
            CurrencyCloudAssignPaymentFeeRequest request
    ) {
        String uri = "/v2/payments/assign_payment_fee";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentDeliveryDate(
            CurrencyCloudGetPaymentDeliveryDateRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/payments/payment_delivery_date", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getPaymentFees(
            CurrencyCloudGetPaymentFeesRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/payments/payment_fees", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getQuotePaymentFee(
            CurrencyCloudGetQuotePaymentFeeRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/payments/quote_payment_fee", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> unassignPaymentFee(
            CurrencyCloudUnassignPaymentFeeRequest request
    ) {
        String uri = "/v2/payments/unassign_payment_fee";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> validatePayment(
            CurrencyCloudValidatePaymentRequest request
    ) {
        String uri = "/v2/payments/validate";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
}