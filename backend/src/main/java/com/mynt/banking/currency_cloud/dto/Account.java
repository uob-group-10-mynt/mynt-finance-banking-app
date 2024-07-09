package com.mynt.banking.currency_cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class Account {

    @JsonProperty("id")
    @NotNull
    @Size(max = 255)
    private String id;

    @JsonProperty("account_name")
    @NotNull
    @Size(max = 255)
    private String accountName;

    @JsonProperty("brand")
    @NotNull
    @Size(max = 255)
    private String brand;

    @JsonProperty("your_reference")
    @Size(max = 255)
    private String yourReference;

    @JsonProperty("status")
    @NotNull
    @Size(max = 255)
    private String status;

    @JsonProperty("street")
    @Size(max = 255)
    private String street;

    @JsonProperty("city")
    @Size(max = 255)
    private String city;

    @JsonProperty("state_or_province")
    @Size(max = 255)
    private String stateOrProvince;

    @JsonProperty("postal_code")
    @Size(max = 20)
    private String postalCode;

    @JsonProperty("country")
    @NotNull
    @Size(max = 2)  // ISO 3166-1 alpha-2 country codes
    private String country;

    @JsonProperty("spread_table")
    @Size(max = 255)
    private String spreadTable;

    @JsonProperty("bank_account_verified")
    @Size(max = 255)
    private String bankAccountVerified;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("order")
    @Size(max = 255)
    private String order;

    @JsonProperty("order_asc_desc")
    @Pattern(regexp = "asc|desc", message = "orderAscDesc must be 'asc' or 'desc'")
    private String orderAscDesc;
}
