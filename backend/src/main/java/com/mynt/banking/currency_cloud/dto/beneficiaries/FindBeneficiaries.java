package com.mynt.banking.currency_cloud.dto.beneficiaries;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Find Beneficiaries")
public class FindBeneficiaries {

    @JsonProperty("name")
    @Size(max = 255)
    @Schema(description = "Beneficiary's name.", example = " ")
    @Builder.Default
    private String name = "";

    @JsonProperty("bank_account_holder_name")
    @Size(max = 255)
    @Schema(description = "Bank account holder's name.", example = " ")
    @Builder.Default
    private String bankAccountHolderName = "";

    @JsonProperty("bank_country")
    @Size(max = 2)
    @Schema(description = "Two-letter country code of the bank", example = " ")
    @Builder.Default
    private String bankCountry = "";


    @JsonProperty("currency")
    @Size(max = 3)
    @Schema(description = "Currency in which money is held in the beneficiary's bank account. Three-letter currency code.", example = " ")
    @Builder.Default
    private String currency = "";

    @JsonProperty("on_behalf_of")
    @Size(max = 36)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

    @JsonProperty("beneficiary_id")
    @Size(max = 36)
    @Schema(description = "Unique identifier of the beneficiary", example = " ")
    @Builder.Default
    private String beneficiaryId = "";


    @JsonProperty("beneficiary_country")
    @Size(max = 2)
    @Schema(description = "Two-letter ISO country code. If the beneficiary is a company, this is the country in which the company is registered. If the beneficiary is an individual, this is the country in which the beneficiary is based.", example = " ")
    @Builder.Default
    private String beneficiaryCountry = "";

    @JsonProperty("beneficiary_entity_type")
    @Size(max = 255)
    @Schema(description = "Entity type of the beneficiary", example = " ")
    @Builder.Default
    private String beneficiaryEntityType = "";

    @JsonProperty("account_number")
    @Size(max = 34)
    @Schema(description = "Bank account number.", example = " ")
    @Builder.Default
    private String accountNumber = "";

    @JsonProperty("routing_code_type[0]")
    @Size(max = 255)
    @Schema(description = "Local payment routing system. If supplied, a value for routing_code_value[0] must also be supplied.", example = " ")
    @Builder.Default
    private String routingCodeType = "";

    @JsonProperty("routing_code_value[0]")
    @Size(max = 255)
    @Schema(description = "Local payment routing system. If supplied, a value for routing_code_value[0] must also be supplied.", example = " ")
    @Builder.Default
    private String routingCodeValue = "";

    @JsonProperty("iban")
    @Size(max = 34)
    @Schema(description = "International Bank Account Number", example = " ")
    @Builder.Default
    private String iban = "";

    @JsonProperty("bic_swift")
    @Size(max = 11)
    @Schema(description = "BIC/Swift code", example = " ")
    @Builder.Default
    private String bicSwift = "";

    @JsonProperty("default_beneficiary")
    @Schema(description = "Payments are made automatically to default beneficiaries when a beneficiary is not specified.", example = " ")
    @Builder.Default
    private Boolean defaultBeneficiary = false;

    @JsonProperty("bank_address")
    @Size(max = 255)
    @Schema(description = "Address of the bank", example = " ")
    @Builder.Default
    private String bankAddress = "";

    @JsonProperty("bank_name")
    @Size(max = 255)
    @Schema(description = "Bank name", example = " ")
    @Builder.Default
    private String bankName = "";

    @JsonProperty("bank_account_type")
    @Size(max = 255)
    @Schema(description = "Bank account type.", example = " ")
    @Builder.Default
    private String bankAccountType = "";

    @JsonProperty("beneficiary_company_name")
    @Size(max = 255)
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"company\".", example = " ")
    @Builder.Default
    private String beneficiaryCompanyName = "";

    @JsonProperty("beneficiary_first_name")
    @Size(max = 255)
    @Schema(description = "Bank name", example = " ")
    @Builder.Default
    private String beneficiaryFirstName = "";

    @JsonProperty("beneficiary_last_name")
    @Size(max = 255)
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"individual\".", example = " ")
    @Builder.Default
    private String beneficiaryLastName = "";

    @JsonProperty("beneficiary_city")
    @Size(max = 255)
    @Schema(description = "city", example = " ")
    @Builder.Default
    private String beneficiaryCity = "";

    @JsonProperty("beneficiary_postcode")
    @Size(max = 255)
    @Schema(description = "Post code", example = " ")
    @Builder.Default
    private String beneficiaryPostcode = "";

    @JsonProperty("beneficiary_state_or_province")
    @Size(max = 255)
    @Schema(description = "State or province.", example = " ")
    @Builder.Default
    private String beneficiaryStateOrProvince = "";

    @JsonProperty("beneficiary_date_of_birth")
    @Size(max = 255)
    @Schema(description = "If \"beneficiary_entity_type\" is \"company\", date of registration. If \"beneficiary_entity_type\" is \"individual\", date of birth. ISO 8601 format YYYY-MM-DD.", example = " ")
    @Builder.Default
    private String beneficiaryDateOfBirth = "";

    @JsonProperty("scope")
    @Size(max = 255)
    @Schema(description = "Search \"own\" account, \"clients\" sub-accounts, or \"all\" accounts.", example = " ")
    @Builder.Default
    private String scope = "";

    @JsonProperty("page")
    @Size(max = 255)
    @Schema(description = "Page number", example = " ")
    @Builder.Default
    private String page = "";

    @JsonProperty("per_page")
    @Size(max = 255)
    @Schema(description = "Number of results per page.", example = " ")
    @Builder.Default
    private String perPage = "";

    @JsonProperty("order")
    @Size(max = 255)
    @Schema(description = "Any field name to change the sort order.", example = " ")
    @Builder.Default
    private String order = "";

    @JsonProperty("order_asc_desc")
    @Size(max = 255)
    @Schema(description = "Sort records in ascending or descending order.", example = " ")
    @Builder.Default
    private String orderAscDesc = "";

    @JsonProperty("beneficiary_external_reference")
    @Size(max = 255)
    @Schema(description = "Beneficiary external reference.", example = " ")
    @Builder.Default
    private String beneficiaryExternalReference = "";



}
