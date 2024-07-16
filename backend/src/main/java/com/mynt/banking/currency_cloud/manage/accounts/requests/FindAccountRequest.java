package com.mynt.banking.currency_cloud.manage.accounts.requests;

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
    @Builder.Default
    private String accountName = "";

    @JsonProperty("brand")
    @Builder.Default
    private String brand = "";

    @JsonProperty("your_reference")
    @Builder.Default
    private String yourReference = "";

    @JsonProperty("status")
    @Builder.Default
    private String status = "enabled";

    @JsonProperty("street")
    @Builder.Default
    private String street = "";

    @JsonProperty("city")
    @Builder.Default
    private String city = "";

    @JsonProperty("state_or_province")
    @Builder.Default
    private String stateOrProvince = "";

    @JsonProperty("postal_code")
    @Builder.Default
    private String postalCode = "";

    @JsonProperty("country")
    @Builder.Default
    private String country = "";

    @JsonProperty("spread_table")
    @Builder.Default
    private String spreadTable = "";

    @JsonProperty("bank_account_verified")
    @Builder.Default
    private String bankAccountVerified = "";

    @JsonProperty("page")
    @Builder.Default
    private Integer page = null;

    @JsonProperty("per_page")
    @Builder.Default
    private Integer perPage = null;

    @JsonProperty("order")
    @Builder.Default
    private String order = "";

    @JsonProperty("order_asc_desc")
    @Builder.Default
    private String orderAscDesc = "";
}
