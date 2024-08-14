package com.mynt.banking.currency_cloud.manage.transactions;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FindTransactionRequest {

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

    @JsonProperty("currency")
    @Size(max = 255)
    @Schema(description = "Three-letter ISO currency code.", example = " ")
    @Builder.Default
    private String currency = "";

    @JsonProperty("amount")
    @Size(max = 255)
    @Schema(description = "Amount the transaction is for.", example = " ")
    @Builder.Default
    private String amount = "";

    @JsonProperty("amount_from")
    @Size(max = 255)
    @Schema(description = "Minimum amount", example = " ")
    @Builder.Default
    private String amountFrom = "";

    @JsonProperty("amount_to")
    @Size(max = 255)
    @Schema(description = "Maximum amount", example = " ")
    @Builder.Default
    private String amountTo = "";

    @JsonProperty("action")
    @Size(max = 255)
    @Schema(description = "The action that triggered the transaction.", example = " ")
    @Builder.Default
    private String action = "";

    @JsonProperty("related_entity_type")
    @Size(max = 255)
    @Schema(description = "None", example = " ")
    @Builder.Default
    private String relatedEntityType = "";

    @JsonProperty("related_entity_id")
    @Size(max = 255)
    @Schema(description = "UUID of the related entity.", example = " ")
    @Builder.Default
    private String relatedEntityId = "";

    @JsonProperty("related_entity_short_reference")
    @Size(max = 255)
    @Schema(description = "Short reference code.", example = " ")
    @Builder.Default
    private String relatedEntityShortReference = "";

    @JsonProperty("status")
    @Size(max = 255)
    @Schema(description = "Transaction status.", example = " ")
    @Builder.Default
    private String status = "";

    @JsonProperty("type")
    @Size(max = 255)
    @Schema(description = "Whether the transaction debits or credits the account balance.", example = " ")
    @Builder.Default
    private String type = "";

    @JsonProperty("reason")
    @Size(max = 255)
    @Schema(description = "User-generated reason for payment - freeform text.", example = " ")
    @Builder.Default
    private String reason = "";

    @JsonProperty("settles_at_from")
    @Size(max = 255)
    @Schema(description = "Earliest processing date. Any valid ISO 8601 format, e.g. \"e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String settlesAtFrom = "";

    @JsonProperty("settles_at_to")
    @Size(max = 255)
    @Schema(description = "Latest processing date. Any valid ISO 8601 format, e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String settlesAtTo = "";

    @JsonProperty("created_at_from")
    @Size(max = 255)
    @Schema(description = "Any valid ISO 8601 format, e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String createdAtFrom = "";

    @JsonProperty("created_at_to")
    @Size(max = 255)
    @Schema(description = "Any valid ISO 8601 format, e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String createdAtTo = "";

    @JsonProperty("updated_at_from")
    @Size(max = 255)
    @Schema(description = "Any valid ISO 8601 format, e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String updatedAtFrom = "";

    @JsonProperty("updated_at_to")
    @Size(max = 255)
    @Schema(description = "Any valid ISO 8601 format, e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String updatedAtTo = "";

    @JsonProperty("completed_at_from")
    @Size(max = 255)
    @Schema(description = "Any valid ISO 8601 format, e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String completedAtFrom = "";

    @JsonProperty("completed_at_to")
    @Size(max = 255)
    @Schema(description = "Any valid ISO 8601 format, e.g. \"2023-12-31T23:59:59Z\".", example = " ")
    @Builder.Default
    private String completedAtTo = "";

    @JsonProperty("beneficiary_id")
    @Size(max = 255)
    @Schema(description = "Beneficiary UUID. Required if \"related_entity_type\" is \"payment\".", example = " ")
    @Builder.Default
    private String beneficiaryId = "";

    @JsonProperty("currency_pair")
    @Size(max = 255)
    @Schema(description = "Concatenated string of the two currencies traded, e.g. \"USDEUR\". Required if \"related_entity_type\" is \"conversion\".", example = " ")
    @Builder.Default
    private String currencyPair = "";

    @JsonProperty("scope")
    @Size(max = 255)
    @Schema(description = "\"Own\" account, \"clients\" sub-accounts, or \"all\" accounts.", example = " ")
    @Builder.Default
    private String scope = "";

    //@NotNull
    @JsonProperty("page")
    @Size(max = 255)
    @Schema(description = "Page number", example = " ")
    @Builder.Default
    private String page = "";

    //@NotNull
    @JsonProperty("per_page")
    @Size(max = 255)
    @Schema(description = "Number of results per page.", example = " ")
    @Builder.Default
    private String perPage = "";

    //@NotNull
    @JsonProperty("order")
    @Size(max = 255)
    @Schema(description = "None", example = " ")
    @Builder.Default
    private String order = "";

    //@NotNull
    @JsonProperty("order_asc_desc")
    @Size(max = 255)
    @Schema(description = "Sort results in ascending or descending order.", example = " ")
    @Builder.Default
    private String orderAscDesc = "";

}
