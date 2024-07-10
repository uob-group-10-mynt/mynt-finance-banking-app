package com.mynt.banking.currency_cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindAccountResponse {

    @JsonProperty("accounts")
    private List<Account> accounts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Account {

        @JsonProperty("id")
        private String id;

        @JsonProperty("account_name")
        private String accountName;

        @JsonProperty("brand")
        private String brand;

        @JsonProperty("your_reference")
        private String yourReference;

        @JsonProperty("status")
        private String status;

        @JsonProperty("street")
        private String street;

        @JsonProperty("city")
        private String city;

        @JsonProperty("state_or_province")
        private String stateOrProvince;

        @JsonProperty("country")
        private String country;

        @JsonProperty("postal_code")
        private String postalCode;

        @JsonProperty("spread_table")
        private String spreadTable;

        @JsonProperty("legal_entity_type")
        private String legalEntityType;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("identification_type")
        private String identificationType;

        @JsonProperty("identification_value")
        private String identificationValue;

        @JsonProperty("short_reference")
        private String shortReference;

        @JsonProperty("api_trading")
        private Boolean apiTrading;

        @JsonProperty("online_trading")
        private Boolean onlineTrading;

        @JsonProperty("phone_trading")
        private Boolean phoneTrading;

        @JsonProperty("process_third_party_funds")
        private Boolean processThirdPartyFunds;

        @JsonProperty("settlement_type")
        private String settlementType;

        @JsonProperty("agent_or_reliance")
        private Boolean agentOrReliance;

        @JsonProperty("terms_and_conditions_accepted")
        private Boolean termsAndConditionsAccepted;

        @JsonProperty("bank_account_verified")
        private String bankAccountVerified;
    }
}
