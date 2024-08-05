package com.mynt.banking.client.manage.transactions;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class TransactionsDetailResponse {
    private List<Transaction> transactions;
    private Pagination pagination;

    // Static inner class for TransactionDTO
    @Data
    @Builder
    public static class Transaction {
        private String id;
        private String accountId;
        private String currency;
        private String amount;
        private String balanceAmount;
        private String type;
        private String relatedEntityType;
        private String relatedEntityId;
        private String relatedEntityShortReference;
        private String status;
        private String reason;
        private String createdAt;
        private String action;
    }

    // Static inner class for Pagination
    @Data
    @Builder
    public static class Pagination {
        private int totalEntries;
        private int totalPages;
        private int currentPage;
        private int perPage;
        private int previousPage;
        private int nextPage;
        private String order;
        private String orderAscDesc;
    }
}
