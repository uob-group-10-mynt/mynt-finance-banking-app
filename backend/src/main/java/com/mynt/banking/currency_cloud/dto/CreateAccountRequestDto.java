package com.mynt.banking.currency_cloud.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAccountRequestDto {

    @NotNull
    private String Account_name;

    @NotNull
    private String legal_entity_type;

    @NotNull
    private String street;

    @NotNull
    private String city;

    private String postalCode;

    @NotNull
    private String country;

    private String brand;

    private String your_reference;

    private String status;

    private String state_or_province;

    private String spread_table;

    private Boolean api_trading;

    private Boolean online_trading;

    private Boolean phone_trading;

    private String identification_type;

    private String identification_value;

    private Boolean termsAndConditionsAccepted;

}
