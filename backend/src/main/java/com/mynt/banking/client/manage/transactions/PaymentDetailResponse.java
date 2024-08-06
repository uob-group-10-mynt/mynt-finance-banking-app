package com.mynt.banking.client.manage.transactions;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentDetailResponse {
    private String id;
    private String amount;
    private String currency;
    private BeneficiaryAccountDetails beneficiaryAccountDetails;
    private PayerAccountDetails payerAccountDetails;
    private String reference;
    private String reason;
    private String status;
    private String paymentType;
    private String paymentDate;
    private String shortReference;
    private String createdAt;
    private String reviewStatus;

    // Static inner class for BeneficiaryAccountDetails
    @Data
    @Builder
    public static class BeneficiaryAccountDetails {
        private String accountHolderName;
        private String bankCountry;
        private String bankName;
        private String bicSwift;
        private String iban;
    }

    // Static inner class for PayerAccountDetails
    @Data
    @Builder
    public static class PayerAccountDetails {
        private String accountHolderName;
        private String bankCountry;
        private String bankName;
        private String accountNumberType;
        private String accountNumber;
        private String routingCodeType;
        private String routingCode;
    }
}
