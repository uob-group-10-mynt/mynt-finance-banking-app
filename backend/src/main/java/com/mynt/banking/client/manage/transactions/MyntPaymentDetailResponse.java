package com.mynt.banking.client.manage.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class MyntPaymentDetailResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("beneficiary_account_details")
    private BeneficiaryAccountDetails beneficiaryAccountDetails;
    @JsonProperty("payer_account_details")
    private PayerAccountDetails payerAccountDetails;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("status")
    private String status;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("payment_date")
    private String paymentDate;
    @JsonProperty("short_reference")
    private String shortReference;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("review_status")
    private String reviewStatus;

    // Static inner class for BeneficiaryAccountDetails
    @Data
    @Builder
    public static class BeneficiaryAccountDetails {
        @JsonProperty("account_holder_name")
        private String accountHolderName;
        @JsonProperty("bank_country")
        private String bankCountry;
        @JsonProperty("bank_name")
        private String bankName;
        @JsonProperty("bic_swift")
        private String bicSwift;
        @JsonProperty("iban")
        private String iban;
    }

    // Static inner class for PayerAccountDetails
    @Data
    @Builder
    public static class PayerAccountDetails {
        @JsonProperty("account_holder_name")
        private String accountHolderName;
        @JsonProperty("bank_country")
        private String bankCountry;
        @JsonProperty("bank_name")
        private String bankName;
        @JsonProperty("account_number_type")
        private String accountNumberType;
        @JsonProperty("account_number")
        private String accountNumber;
        @JsonProperty("routing_code_type")
        private String routingCodeType;
        @JsonProperty("routing_code")
        private String routingCode;
    }
}
