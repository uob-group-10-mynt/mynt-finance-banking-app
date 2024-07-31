package com.mynt.banking.currency_cloud.collect.funding.requests;

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
public class FindAccountDetails {

    @JsonProperty("payment_type")
    @Schema(description = "Type of payment method used", example = "priority")
    @Size(max = 255)
    @Builder.Default
    private String paymentType = "priority";

    @JsonProperty("currency")
    @NotNull
    @Schema(description = "The currency of the account details required, ISO 4217 standard", example = "GBP")
    @Size(max = 3)
    @Builder.Default
    private String currency = "GBP";

    @JsonProperty("account_id")
    @Schema(description = "UUID of a specific sub-account you want to see the SSIs for", example = " ")
    @Builder.Default
    private String accountId = "";

    @JsonProperty("on_behalf_of")
    @Schema(description = "Indicates if the request is made on behalf of another entity", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

    @JsonProperty("page")
    @Schema(description = "Page number for pagination", example = "1")
    @Builder.Default
    private Integer page = 1;

    @JsonProperty("per_page")
    @Schema(description = "Number of items per page", example = "10")
    @Builder.Default
    private Integer perPage = 10;

    @JsonProperty("order")
    @Schema(description = "Field to order results by", example = " ")
    @Builder.Default
    private String order = "";

    @JsonProperty("order_asc_desc")
    @Schema(description = "Specify the order direction (asc or desc)", example = " ")
    @Builder.Default
    private String orderAscDesc = "";
}
