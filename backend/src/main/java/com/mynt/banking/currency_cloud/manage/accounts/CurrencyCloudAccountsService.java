
package com.mynt.banking.currency_cloud.manage.accounts;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.CurrencyCloudAuthenticator;
import com.mynt.banking.currency_cloud.config.CurrencyCloudClient;
import com.mynt.banking.currency_cloud.manage.accounts.requests.*;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyCloudAccountsService {
    private final CurrencyCloudClient currencyCloudClient;
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    
    public ResponseEntity<JsonNode> createAccount(
            CurrencyCloudCreateAccountRequest request
    ) {
        String uri = "/v2/accounts/create";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getAccount(
            String id,
			CurrencyCloudGetAccountRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/accounts/" + id + "", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> updateAccount(
            String id,
			CurrencyCloudUpdateAccountRequest request
    ) {
        String uri = "/v2/accounts/" + id + "";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getAuthenticatingAccount(
            
    ) {
        String uri = "/v2/accounts/current";
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> findAccounts(
            CurrencyCloudFindAccountsRequest request
    ) {
        String uri = "/v2/accounts/find";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> getThePaymentChargesSettingsForAGivenAccount(
            String account_id
    ) {
        String uri = "/v2/accounts/" + account_id + "/payment_charges_settings";
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> manageAccountPaymentChargesSettings(
            String account_id,
			String charge_settings_id,
			CurrencyCloudManageAccountPaymentChargesSettingsRequest request
    ) {
        String uri = "/v2/accounts/" + account_id + "/payment_charges_settings/" + charge_settings_id + "";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
}