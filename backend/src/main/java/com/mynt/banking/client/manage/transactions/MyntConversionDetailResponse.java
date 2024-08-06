package com.mynt.banking.client.manage.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyntConversionDetailResponse {
    @JsonIgnore
    private String id;

    @JsonProperty("settlement_date")
    private OffsetDateTime settlementDate;

    @JsonProperty("short_reference")
    private String shortReference;

    @JsonProperty("status")
    private String status;

    @JsonProperty("buy_currency")
    private String buyCurrency;

    @JsonProperty("sell_currency")
    private String sellCurrency;

    @JsonProperty("client_buy_amount")
    private String clientBuyAmount;

    @JsonProperty("client_sell_amount")
    private String clientSellAmount;

    @JsonProperty("core_rate")
    private String coreRate;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonIgnore
    private String partnerRate;

    @JsonIgnore
    private String partnerBuyAmount;

    @JsonIgnore
    private String partnerSellAmount;

    @JsonIgnore
    private boolean depositRequired;

    @JsonIgnore
    private String depositAmount;

    @JsonIgnore
    private String depositCurrency;

    @JsonIgnore
    private String depositStatus;

    @JsonIgnore
    private String depositRequiredAt;

    @JsonIgnore
    private List<String> paymentIds;

    @JsonIgnore
    private String unallocatedFunds;

    @JsonIgnore
    private String uniqueRequestId;

    @JsonIgnore
    private String midMarketRate;
}
