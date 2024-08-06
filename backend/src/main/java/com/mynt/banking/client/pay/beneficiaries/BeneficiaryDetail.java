package com.mynt.banking.client.pay.beneficiaries;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
public class BeneficiaryDetail {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty("bank_country")
    private String bankCountry;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("beneficiary_address")
    private String beneficiaryAddress;

    @JsonProperty("beneficiary_country")
    private String beneficiaryCountry;

    @JsonProperty("bic_swift")
    private String bicSwift;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("beneficiary_first_name")
    private String beneficiaryFirstName;

    @JsonProperty("beneficiary_last_name")
    private String beneficiaryLastName;

    @JsonProperty("beneficiary_city")
    private String beneficiaryCity;

    @JsonProperty("beneficiary_postcode")
    private String beneficiaryPostcode;
}
