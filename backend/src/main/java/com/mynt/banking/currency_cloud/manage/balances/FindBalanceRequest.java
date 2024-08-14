package com.mynt.banking.currency_cloud.manage.balances;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FindBalanceRequest {

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

    @JsonProperty("amount_from")
    @Size(max = 255)
    @Schema(description = "Minimum balance amount.", example = " ")
    @Builder.Default
    private String amountFrom = "";

    @JsonProperty("amount_to")
    @Size(max = 255)
    @Schema(description = "Maximum balance amount.", example = " ")
    @Builder.Default
    private String amountTo = "";

    @JsonProperty("as_at_date")
    @Size(max = 255)
    @Schema(description = "A valid ISO 8601 format, e.g. \"2019-12-31T23:59:59\".", example = " ")
    @Builder.Default
    private String asAtDate = "" ;

    @JsonProperty("scope")
    @Schema(description = "\"Own\" account, \"clients\" sub-accounts, or \"all\" accounts.", example = " ")
    @Builder.Default
    private String createdAt = "";

    @JsonProperty("page")
    @Schema(description = "Page number", example = " ")
    @Builder.Default
    private String page = "";

    @JsonProperty("per_page")
    @Schema(description = "Number of results per page.", example = " ")
    @Builder.Default
    private String perPage = "";

    @JsonProperty("order")
    @Schema(description = "A field name to change the sort order - \"created_at\", \"amount\", \"updated_at\" or \"currency\".", example = " ")
    @Builder.Default
    private String order = "";

}
