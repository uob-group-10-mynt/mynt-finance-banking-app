package com.mynt.banking.currency_cloud.collect.demo.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class demoFundingDto {

    @JsonProperty("id")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Identifier for the funding request", example = "12345")
    @Builder.Default
    private String id = "";

    @JsonProperty("sender_name")
    @Size(max = 255)
    @Schema(description = "Name of the sender", example = " ")
    @Builder.Default
    private String senderName = "";

    @JsonProperty("sender_address")
    @Size(max = 255)
    @Schema(description = "Address of the sender", example = " ")
    @Builder.Default
    private String senderAddress = "";

    @JsonProperty("sender_country")
    @Size(max = 255)
    @Schema(description = "Country of the sender", example = " ")
    @Builder.Default
    private String senderCountry = "";

    @JsonProperty("sender_reference")
    @Size(max = 255)
    @Schema(description = "Reference of the sender", example = " ")
    @Builder.Default
    private String senderReference = "";

    @JsonProperty("sender_account_number")
    @Size(max = 255)
    @Schema(description = "Account number of the sender", example = " ")
    @Builder.Default
    private String senderAccountNumber = "";

    @JsonProperty("sender_routing_code")
    @Size(max = 255)
    @Schema(description = "Routing code of the sender's account", example = " ")
    @Builder.Default
    private String senderRoutingCode = "";

    @JsonProperty("receiver_account_number")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Account number of the receiver", example = "987654321")
    @Builder.Default
    private String receiverAccountNumber = "";

    @JsonProperty("receiver_routing_code")
    @Size(max = 255)
    @Schema(description = "Routing code of the receiver's account", example = " ")
    @Builder.Default
    private String receiverRoutingCode = "";

    @JsonProperty("amount")
    @NotNull
    @Schema(description = "Amount to be funded", example = "1000.00")
    @Builder.Default
    private BigDecimal amount = BigDecimal.ZERO;

    @JsonProperty("currency")
    @NotNull
    @Size(max = 3)
    @Schema(description = "Currency of the amount", example = "GBP")
    @Builder.Default
    private String currency = "GBP";

    @JsonProperty("action")
    @Size(max = 255)
    @Schema(description = "Action type for the funding request", example = " ")
    @Builder.Default
    private String action = "";

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "Name of the person or entity on behalf of whom the funding is being requested", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

}
