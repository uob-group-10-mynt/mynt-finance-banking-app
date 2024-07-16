package com.mynt.banking.currency_cloud.manage.accounts.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FindAccountRequest {

    @JsonProperty("account_name")
    @Schema(description = "Account name.", example = " ")
    @Builder.Default
    private String accountName = "";

    @JsonProperty("brand")
    @Schema(description = "The value of this field is used for white labeling the Currencycloud user interface.", example = " ")
    @Builder.Default
    private String brand = "";

    @JsonProperty("your_reference")
    @Schema(description = "User-generated reference code.", example = " ")
    @Builder.Default
    private String yourReference = "";

    @JsonProperty("status")
    @Schema(description = """
            Account status
            
            Enum:
            \tenabled
            \tenabled_no_trading
            \tdisabled
            """, example = " ")
    @Builder.Default
    private String status = "";

    @JsonProperty("street")
    @Schema(description = "First line of address.", example = " ")
    @Builder.Default
    private String street = "";

    @JsonProperty("city")
    @Schema(description = "City.", example = " ")
    @Builder.Default
    private String city = "";

    @JsonProperty("state_or_province")
    @Schema(description = "State or province two-letter ISO 3166 code. Only applicable to some countries.", example = " ")
    @Builder.Default
    private String stateOrProvince = "";

    @JsonProperty("postal_code")
    @Schema(description = "Postal code.", example = " ")
    @Builder.Default
    private String postalCode = "";

    @JsonProperty("country")
    @Schema(description = "Two-letter country code.", example = " ")
    @Builder.Default
    private String country = "";

    @JsonProperty("spread_table")
    @Schema(description = "The name of the spread table assigned to the account.", example = " ")
    @Builder.Default
    private String spreadTable = "";

    @JsonProperty("bank_account_verified")
    @Schema(description = """
                Has the bank account been verified?

                Enum:
                \tyes
                \tno
                \tnot required
        """, example = " ")
    @Builder.Default
    private String bankAccountVerified = "";

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
    private String order ="";

    @JsonProperty("order_asc_desc")
    @Schema(description = """
            Sort records in ascending or descending order.
            
            Enum:
            \tasc
            \tdesc
            """, example = " ")
    @Builder.Default
    private String orderAscDesc ="";
}
