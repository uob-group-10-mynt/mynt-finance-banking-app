package com.mynt.banking.currency_cloud.pay.beneficiaries.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@Schema(description = "Find Beneficiary Request DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindBeneficiaryRequest {

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.",
            example = "36ed7b74-09dc-4a32-b4a6-1a4e44d90eaa")
    @Builder.Default
    private String onBehalfOf = "";

    @JsonProperty("bank_account_holder_name")
    @Size(max = 255)
    @Schema(description = "Bank account holder's name.",
            example = "Kenyan User02")
    @Builder.Default
    private String bankAccountHolderName = "";

    @JsonProperty("beneficiary_country")
    @Size(min = 2, max = 2)
    @Schema(description = "Two-letter ISO country code. If the beneficiary is a company, this is the country in which the company is registered. //" +
                        "If the beneficiary is an individual, this is the country in which the beneficiary is based.",
            example = "KE")
    @Builder.Default
    private String beneficiaryCountry = "";

    @JsonProperty("currency")
    @Size(min = 3, max = 3)
    @Schema(description = "Currency in which money is held in the beneficiary's bank account. Three-letter currency code.",
            example = "KES")
    @Builder.Default
    private String currency = "";

    @JsonProperty("account_number")
    @Size(max = 255)
    @Schema(description = "Bank account number.", example = " ")
    @Builder.Default
    private String accountNumber = "";

    @JsonProperty("routing_code_type[0]")
    @Schema(description = """
            Local payment routing system. If supplied, a value for routing_code_value[0] must also be supplied.

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
    private String[] routingCodeType = null;

    @JsonProperty("routing_code_value[0]")
    @Schema(description = "The value for any routing system specified in routing_code_type[0]. If supplied, " +
            "a value for routing_code_type[0] must also be supplied.", example = " ")
    @Builder.Default
    private String[] routingCodeValue = null;

    @JsonProperty("bic_swift")
    @Size(max = 255)
    @Schema(description = "BIC/Swift code", example = "TCCLGB3L")
    @Builder.Default
    private String bicSwift = null;

    @JsonProperty("iban")
    @Size(max = 255)
    @Schema(description = "IBAN code", example = "GB41TCCL12345673185203")
    @Builder.Default
    private String iban = "";

    @JsonProperty("default_beneficiary")
    @Schema(description = "Payments are made automatically to default beneficiaries when a beneficiary is not specified.", example = "false")
    @Builder.Default
    private Boolean defaultBeneficiary = null;

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

    @JsonProperty("name")
    @Schema(description = "Beneficiary's name.", example = " ")
    @Builder.Default
    private String name = "";

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
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"individual\".", example = "Kenyan")
    @Builder.Default
    private String beneficiaryFirstName = "";

    @JsonProperty("beneficiary_last_name")
    @Schema(description = "Required if \"beneficiary_entity_type\" is \"individual\".", example = "User")
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

    @JsonProperty("scope")
    @Schema(description = """
            Search "own" account, "clients" sub-accounts, or "all" accounts.

            Enum:\
            \town
            \tclients
            \tall""", example = " ")
    @Builder.Default
    private String scope = "";

    @JsonProperty("page")
    @Schema(description = "Page number.", example = " ")
    @Builder.Default
    private Integer page = null;

    @JsonProperty("per_page")
    @Schema(description = "Number of results per page.", example = " ")
    @Builder.Default
    private Integer perPage = null;

    @JsonProperty("order")
    @Schema(description = "Any field name to change the sort order.", example = " ")
    @Builder.Default
    private String order = "";

    @JsonProperty("order_asc_desc")
    @Schema(description = "Order direction (asc/desc).", example = " ")
    @Builder.Default
    private String orderAscDesc = "";

    @JsonProperty("beneficiary_external_reference")
    @Schema(description = "External reference for the beneficiary.", example = " ")
    @Builder.Default
    private String beneficiaryExternalReference = "";
}
