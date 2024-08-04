package com.mynt.banking.client.manage.transactions;

import lombok.Builder;
import lombok.Data;

@Data
public class PaymentDetailResponse {
    private Transaction transaction;

    // Static inner class for Transaction
    @Data
    @Builder
    public static class Transaction {
        private String id;
        private String currency;
        private String amount;
        private String balanceAmount;
        private String type;
        private String relatedEntityType;
        private String relatedEntityShortReference;
        private String status;
        private String reason;
        private String createdAt;
        private String action;
        private PayerDetails payerDetails;
        private BeneficiaryDetails beneficiaryDetails;

        // Static inner class for PayerDetails
        @Data
        @Builder
        public static class PayerDetails {
            private String id;
            private String legalEntityType;
            private String companyName; // or null if individual
            private String country;
            private String firstName;
            private String lastName;
            private String accountName;
            private String brand;
        }

        // Static inner class for BeneficiaryDetails
        @Data
        @Builder
        public static class BeneficiaryDetails {
            private String id;
            private String bankAccountHolderName;
            private String name;
            private String bicSwift;
            private String iban;
        }
    }
}

