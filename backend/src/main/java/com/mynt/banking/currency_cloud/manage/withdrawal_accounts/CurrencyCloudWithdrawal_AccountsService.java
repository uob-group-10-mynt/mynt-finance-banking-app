
package com.mynt.banking.currency_cloud.manage.withdrawal_accounts;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.CurrencyCloudAuthenticator;
import com.mynt.banking.currency_cloud.config.CurrencyCloudClient;
import com.mynt.banking.currency_cloud.manage.withdrawal_accounts.requests.*;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyCloudWithdrawal_AccountsService {
    private final CurrencyCloudClient currencyCloudClient;
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    
    public ResponseEntity<JsonNode> findWithdrawalAccount(
            CurrencyCloudFindWithdrawalAccountRequest request
    ) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/withdrawal_accounts/find", request);
        return currencyCloudClient.getClient()
                .get()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
    public ResponseEntity<JsonNode> pullFundsFromWithdrawalAccount(
            String withdrawal_account_id,
			CurrencyCloudPullFundsFromWithdrawalAccountRequest request
    ) {
        String uri = "/v2/withdrawal_accounts/" + withdrawal_account_id + "/pull_funds";
        return currencyCloudClient.getClient()
                .post()
                .uri(uri)
                .header("X-Auth-Token", currencyCloudAuthenticator.getAuthToken())
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
    
}