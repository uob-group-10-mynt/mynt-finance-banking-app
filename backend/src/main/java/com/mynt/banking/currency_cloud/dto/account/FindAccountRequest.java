package com.mynt.banking.currency_cloud.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindAccountRequest {

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("brand")
    @Builder.Default
    private String brand = "currencycloud";

    @JsonProperty("your_reference")
    private String yourReference;

    @JsonProperty("status")
    @Builder.Default
    private String status = "enabled";

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state_or_province")
    private String stateOrProvince;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("spread_table")
    private String spreadTable;

    @JsonProperty("bank_account_verified")
    private String bankAccountVerified;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("order")
    private String order;

    @JsonProperty("order_asc_desc")
    private String orderAscDesc;
}
