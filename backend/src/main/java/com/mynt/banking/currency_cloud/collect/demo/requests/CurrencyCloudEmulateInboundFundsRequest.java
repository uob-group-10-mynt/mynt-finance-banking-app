
package com.mynt.banking.currency_cloud.collect.demo.requests;

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
public class CurrencyCloudEmulateInboundFundsRequest {
    
    @NotNull
    @JsonProperty("id")
    @Size(max = 255)
    @Builder.Default
    private String id = "";
    
    //@NotNull
    @JsonProperty("sender_name")
    @Size(max = 255)
    @Builder.Default
    private String senderName = "";
    
    //@NotNull
    @JsonProperty("sender_address")
    @Size(max = 255)
    @Builder.Default
    private String senderAddress = "";
    
    //@NotNull
    @JsonProperty("sender_country")
    @Size(max = 255)
    @Builder.Default
    private String senderCountry = "";
    
    //@NotNull
    @JsonProperty("sender_reference")
    @Size(max = 255)
    @Builder.Default
    private String senderReference = "";
    
    //@NotNull
    @JsonProperty("sender_account_number")
    @Size(max = 255)
    @Builder.Default
    private String senderAccountNumber = "";
    
    //@NotNull
    @JsonProperty("sender_routing_code")
    @Size(max = 255)
    @Builder.Default
    private String senderRoutingCode = "";
    
    @NotNull
    @JsonProperty("receiver_account_number")
    @Size(max = 255)
    @Builder.Default
    private String receiverAccountNumber = "";
    
    //@NotNull
    @JsonProperty("receiver_routing_code")
    @Size(max = 255)
    @Builder.Default
    private String receiverRoutingCode = "";
    
    @NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Builder.Default
    private String amount = "";
    
    @NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    //@NotNull
    @JsonProperty("action")
    @Size(max = 255)
    @Builder.Default
    private String action = "";
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
}