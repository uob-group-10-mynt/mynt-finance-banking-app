package com.mynt.banking.currency_cloud.pay.beneficiaries;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Validate Beneficiary Request DTO")
public class ValidateBeneficiaryRequest {

    @JsonProperty("bank_country")
    @Size(max = 2)
    @NotNull
    @Schema(description = "Two-letter country code of the bank.", example = "KE")
    @Builder.Default
    private String bankCountry = "";

    @JsonProperty("currency")
    @Size(max = 3)
    @NotNull
    @Schema(description = "Currency in which money is held in the beneficiary's bank account. Three-letter currency code.", example = "KES")
    @Builder.Default
    private String currency = "KES";

    @JsonProperty("beneficiary_address")
    @Size(max = 255)
    @Schema(description = "Address of the beneficiary.", example = " ")
    private String beneficiaryAddress;

    @JsonProperty("beneficiary_country")
    @Size(max = 2)
    @Schema(description = "Two-letter ISO country code. If the beneficiary is a company, this is the country in which the company is registered. " +
            "If the beneficiary is an individual, this is the country in which the beneficiary is based.", example = "KE")
    @Builder.Default
    private String beneficiaryCountry = "";

    @JsonProperty("account_number")
    @Size(max = 34)
    @Schema(description = "Bank account number.", example = " ")
    @Builder.Default
    private String accountNumber = "";

    @JsonProperty("routing_code_type_1")
    @Size(max = 255)
    @Schema(description = "Local payment routing system. If supplied, a value for routing_code_value_1 must also be supplied.", example = " ")
    @Builder.Default
    private String routingCodeType1 = "";

    @JsonProperty("routing_code_value_1")
    @Size(max = 255)
    @Schema(description = "Local payment routing system value. If supplied, a value for routing_code_type_1 must also be supplied.", example = " ")
    @Builder.Default
    private String routingCodeValue1 = "";

    @JsonProperty("routing_code_type_2")
    @Size(max = 255)
    @Schema(description = "Secondary local payment routing system. If supplied, a value for routing_code_value_2 must also be supplied.", example = " ")
    @Builder.Default
    private String routingCodeType2 = "";

    @JsonProperty("routing_code_value_2")
    @Size(max = 255)
    @Schema(description = "Secondary local payment routing system value. If supplied, a value for routing_code_type_2 must also be supplied.", example = " ")
    @Builder.Default
    private String routingCodeValue2 = "";

    @JsonProperty("bic_swift")
    @Size(max = 11)
    @Schema(description = "BIC/Swift code.", example = " ")
    @Builder.Default
    private String bicSwift = "";

    @JsonProperty("iban")
    @Size(max = 34)
    @Schema(description = "International Bank Account Number.", example = " ")
    @Builder.Default
    private String iban = "";

    @JsonProperty("bank_address")
    @Size(max = 255)
    @Schema(description = "Address of the bank.", example = " ")
    @Builder.Default
    private String bankAddress = "";

    @JsonProperty("bank_name")
    @Size(max = 255)
    @Schema(description = "Bank name.", example = " ")
    @Builder.Default
    private String bankName = "";

    @JsonProperty("bank_account_type")
    @Schema(description = """
            Bank account type.

            Enum:\
            \tchecking
            \tsavings""", example = " ")
    @Builder.Default
    private String bankAccountType = "";

    @JsonProperty("beneficiary_entity_type")
    @Size(max = 255)
    @Schema(description = "Entity type of the beneficiary.", example = "individual")
    @Builder.Default
    private String beneficiaryEntityType = "individual";

    @JsonProperty("beneficiary_company_name")
    @Size(max = 255)
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"company\".", example = " ")
    @Builder.Default
    private String beneficiaryCompanyName = "";

    @JsonProperty("beneficiary_first_name")
    @Size(max = 255)
    @Schema(description = "Beneficiary first name.", example = " ")
    @Builder.Default
    private String beneficiaryFirstName = "";

    @JsonProperty("beneficiary_last_name")
    @Size(max = 255)
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"individual\".", example = " ")
    @Builder.Default
    private String beneficiaryLastName = "";

    @JsonProperty("beneficiary_city")
    @Size(max = 255)
    @Schema(description = "City.", example = " ")
    @Builder.Default
    private String beneficiaryCity = "";

    @JsonProperty("beneficiary_postcode")
    @Size(max = 255)
    @Schema(description = "Post code.", example = " ")
    @Builder.Default
    private String beneficiaryPostcode = "";

    @JsonProperty("beneficiary_state_or_province")
    @Size(max = 255)
    @Schema(description = "State or province.", example = " ")
    @Builder.Default
    private String beneficiaryStateOrProvince = "";

    @JsonProperty("beneficiary_date_of_birth")
    @Size(max = 255)
    @Schema(description = "If \"beneficiary_entity_type\" is \"company\", " +
            "date of registration. If \"beneficiary_entity_type\" is \"individual\", date of birth. ISO 8601 format YYYY-MM-DD.", example = " ")
    @Builder.Default
    private String beneficiaryDateOfBirth = "";

    @JsonProperty("beneficiary_identification_type")
    @Size(max = 255)
    @Schema(description = """
            A legal document that verifies the identity of the beneficiary. Required documentation will \
            vary depending on the country in which the beneficiary's bank account is held.

            Enum
            none
            drivers_license
            social_security_number
            green_card
            passport
            visa
            matricula_consular
            registro_federal_de_contribuyentes
            clave_unica_de_registro_de_poblacion
            credential_de_elector
            social_insurance_number
            citizenship_papers
            drivers_license_canadian
            existing_credit_card_details
            employer_identification_number
            national_id
            incorporation_number
            others""", example = " ")
    @Builder.Default
    private String beneficiaryIdentificationType = "";

    @JsonProperty("beneficiary_identification_value")
    @Size(max = 255)
    @Schema(description = "A unique reference code for the identification document, such as a passport number.", example = " ")
    @Builder.Default
    private String beneficiaryIdentificationValue = "";

    @JsonProperty("payment_types")
    @Schema(description = "Currencycloud supports two types of payments: \"priority\", made using the Swift network; and \"regular\", made using the local bank network.",
            example = "[\"priority\", \"regular\"]")
    @Builder.Default
    private String[] paymentTypes = null;

    @JsonProperty("on_behalf_of")
    @Size(max = 36)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.", example = " ")
    @Builder.Default
    private String onBehalfOf = "";
}
