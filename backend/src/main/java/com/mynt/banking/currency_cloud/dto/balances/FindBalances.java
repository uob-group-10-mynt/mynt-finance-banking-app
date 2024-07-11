
package com.mynt.banking.currency_cloud.dto.balances;

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
public class FindBalances {

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

}
