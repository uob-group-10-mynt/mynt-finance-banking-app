package com.mynt.banking.currency_cloud.pay.beneficiaries.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Create Beneficiaries Request DTO")
public class CreateBeneficiaryRequest {

    @JsonProperty("name")
    @Size(max = 255)
    @NotNull
    @Schema(description = "Beneficiary's name.", example = " ")
    @Builder.Default
    private String name = "";

    @JsonProperty("bank_account_holder_name")
    @Size(max = 255)
    @NotNull
    @Schema(description = "Bank account holder's name.", example = " ")
    @Builder.Default
    private String bankAccountHolderName = "";

    @JsonProperty("bank_country")
    @Size(max = 2)
    @NotNull
    @Schema(description = "Two-letter country code of the bank", example = " ")
    @Builder.Default
    private String bankCountry = "";

    @JsonProperty("currency")
    @Size(max = 3)
    @NotNull
    @Schema(description = "Currency in which money is held in the beneficiary's bank account. Three-letter currency code.", example = " ")
    @Builder.Default
    private String currency = "";

    @JsonProperty("email")
    @Size(max = 255)
    @Schema(description = "Email address", example = " ")
    @Builder.Default
    private String email = "";

    @JsonProperty("beneficiary_address")
    @Size(max = 255)
    @Schema(description = "First line of address.", example = " ")
    @Builder.Default
    private String beneficiaryAddress = "";

    @JsonProperty("beneficiary_country")
    @Size(min = 2, max = 2)
    @Schema(description = "Two-letter ISO country code. If the beneficiary is a company, this is the country in which the company is registered. //" +
            "If the beneficiary is an individual, this is the country in which the beneficiary is based.",
            example = " ")
    @Builder.Default
    private String beneficiaryCountry = "";

    @JsonProperty("account_number")
    @Size(max = 34)
    @Schema(description = "Bank account number.", example = " ")
    @Builder.Default
    private String accountNumber = "";

    @JsonProperty("routing_code_type_1")
    @Schema(description = """
            Local payment routing system. If supplied, a value for routing_code_value_1 must also be supplied.

            Enum:\
            \tsort_code
            \tbsb_code
            \tinstitution_no
            \tbank_code
            \tbranch_code
            \taba
            \tclabe
            \tcnaps
            \tifsc""", example = " ")
    @Builder.Default
    private String[] routingCodeType1 = null;

    @JsonProperty("routing_code_value_1")
    @Schema(description = "Routing code for routing_code_type_1. If supplied, routing_code_type_1 should also be supplied.", example = " ")
    @Builder.Default
    private String[] routingCodeValue1 = null;

    @JsonProperty("routing_code_type_2")
    @Schema(description = """
            Local payment routing system. If supplied, a value for routing_code_value_1 must also be supplied.

            Enum:\
            \tsort_code
            \tbsb_code
            \tinstitution_no
            \tbank_code
            \tbranch_code
            \taba
            \tclabe
            \tcnaps
            \tifsc""", example = " ")
    @Builder.Default
    private String[] routingCodeType2 = null;

    @JsonProperty("routing_code_value_2")
    @Schema(description = "Routing code for routing_code_type_2. If supplied, routing_code_type_2 should also be supplied.", example = " ")
    @Builder.Default
    private String[] routingCodeValue2 = null;

    @JsonProperty("bic_swift")
    @Size(max = 255)
    @Schema(description = "BIC/Swift code", example = " ")
    @Builder.Default
    private String bicSwift = null;

    @JsonProperty("iban")
    @Size(max = 255)
    @Schema(description = "IBAN code", example = " ")
    @Builder.Default
    private String iban = null;

    @JsonProperty("default_beneficiary")
    @Schema(description = "Payments are made automatically to default beneficiaries when a beneficiary is not specified.", example = " ")
    @Builder.Default
    private Boolean defaultBeneficiary = null;

    @JsonProperty("bank_address")
    @Size(max = 255)
    @Schema(description = "First line of address.", example = " ")
    @Builder.Default
    private String bankAddress = "";

    @JsonProperty("bank_name")
    @Size(max = 255)
    @Schema(description = "Bank name", example = " ")
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
    @Schema(description = """
            Beneficiary's legal entity type - individual or company.

            Enum:
            \tindividual
            \tcompany""",
            example = "individual")
    @Builder.Default
    private String beneficiaryEntityType = "";

    @JsonProperty("beneficiary_company_name")
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"company\".", example = " ")
    @Builder.Default
    private String beneficiaryCompanyName = "";

    @JsonProperty("beneficiary_first_name")
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"individual\".", example = " ")
    @Builder.Default
    private String beneficiaryFirstName = "";

    @JsonProperty("beneficiary_last_name")
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"individual\".", example = " ")
    @Builder.Default
    private String beneficiaryLastName = "";

    @JsonProperty("beneficiary_city")
    @Schema(description = "Beneficiary city.", example = " ")
    @Builder.Default
    private String beneficiaryCity = "";

    @JsonProperty("beneficiary_postcode")
    @Schema(description = "Beneficiary postcode.", example = " ")
    @Builder.Default
    private String beneficiaryPostcode = "";

    @JsonProperty("beneficiary_state_or_province")
    @Schema(description = "Beneficiary state or province.", example = " ")
    @Builder.Default
    private String beneficiaryStateOrProvince = "";

    @JsonProperty("beneficiary_date_of_birth")
    @Schema(description = "If \"beneficiary_entity_type\" is \"company\", date of registration. " +
            "If \"beneficiary_entity_type\" is \"individual\", date of birth. ISO 8601 format YYYY-MM-DD.",
            example = " ")
    @Builder.Default
    private String beneficiaryDateOfBirth = "";

    @JsonProperty("beneficiary_identification_type")
    @Schema(description = "A legal document that verifies the identity of the beneficiary. Required documentation will vary " +
            "depending on the country in which the beneficiar's bank account is " +
            "held and on whether \"beneficiary_entity_type\" is \"individual\" or \"company\".\n" +
            "\n" +
            "Enum\n" +
            "none\n" +
            "drivers_license\n" +
            "social_security_number\n" +
            "green_card\n" +
            "passport\n" +
            "visa\n" +
            "matricula_consular\n" +
            "registro_federal_de_contribuyentes\n" +
            "clave_unica_de_registro_de_poblacion\n" +
            "credential_de_elector\n" +
            "social_insurance_number\n" +
            "citizenship_papers\n" +
            "drivers_license_canadian\n" +
            "existing_credit_card_details\n" +
            "employer_identification_number\n" +
            "national_id\n" +
            "incorporation_number\n" +
            "others", example = " ")
    @Builder.Default
    private String beneficiaryIdentificationType = "";

    @JsonProperty("beneficiary_identification_value")
    @Schema(description = "A unique reference code for the identification document, such as a passport number.", example = " ")
    @Builder.Default
    private String identificationType = "";

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

    @JsonProperty("beneficiary_external_reference")
    @Schema(description = "External reference for the beneficiary.", example = " ")
    @Builder.Default
    private String beneficiaryExternalReference = "";

    @JsonProperty("business_type")
    @Size(max = 255)
    @Schema(description = "Beneficiary nature of business.", example = " ")
    @Builder.Default
    private String businessType = "";

    @JsonProperty("company_website")
    @Size(max = 255)
    @Schema(description = "Beneficiary company website.", example = " ")
    @Builder.Default
    private String companyWebsite = "";
}
