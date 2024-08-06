package com.mynt.banking.client.pay.beneficiaries;

import com.fasterxml.jackson.annotation.*;
import com.mynt.banking.util.FieldViewAnnotation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryDetail {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("bank_account_holder_name")
    private String bankAccountHolderName;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("bank_country")
    private String bankCountry;

    @JsonProperty("bic_swift")
    private String bicSwift;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("beneficiary_address")
    private List<String> beneficiaryAddress;

    @JsonProperty("beneficiary_country")
    private String beneficiaryCountry;

    @JsonProperty("beneficiary_first_name")
    private String beneficiaryFirstName;

    @JsonProperty("beneficiary_last_name")
    private String beneficiaryLastName;

    @JsonProperty("beneficiary_city")
    private String beneficiaryCity;

    @JsonProperty("beneficiary_postcode")
    private String beneficiaryPostcode;
}
