package com.mynt.banking.currency_cloud.manage.balances;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetBalanceRequest {

    @JsonProperty("on_behalf_of")
    @Size(max = 100, message = "Size exceeded for on_behalf_of")
    @Pattern(regexp = "^([A-z0-9]-?)*$", message = "Incorrect format for on_behalf_of")
    private String onBehalfOf;
}
