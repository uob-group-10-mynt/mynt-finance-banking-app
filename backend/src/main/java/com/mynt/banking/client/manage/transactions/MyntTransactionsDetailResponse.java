package com.mynt.banking.client.manage.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class MyntTransactionsDetailResponse {
    @JsonProperty("transactions")
    private List<Transaction> transactions;

    @JsonProperty("pagination")
    private Pagination pagination;

    // Static inner class for Transaction
    @Data
    @Builder
    public static class Transaction {
        @JsonProperty("id")
        private String id;

        @JsonProperty("account_id")
        private String accountId;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("balance_amount")
        private String balanceAmount;

        @JsonProperty("type")
        private String type;

        @JsonProperty("related_entity_type")
        private String relatedEntityType;

        @JsonProperty("related_entity_id")
        private String relatedEntityId;

        @JsonProperty("related_entity_short_reference")
        private String relatedEntityShortReference;

        @JsonProperty("status")
        private String status;

        @JsonProperty("reason")
        private String reason;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("action")
        private String action;
    }

    // Static inner class for Pagination
    @Data
    @Builder
    public static class Pagination {
        @JsonProperty("total_entries")
        private int totalEntries;

        @JsonProperty("total_pages")
        private int totalPages;

        @JsonProperty("current_page")
        private int currentPage;

        @JsonProperty("per_page")
        private int perPage;

        @JsonProperty("previous_page")
        private int previousPage;

        @JsonProperty("next_page")
        private int nextPage;

        @JsonProperty("order")
        private String order;

        @JsonProperty("order_asc_desc")
        @Builder.Default
        private String orderAscDesc = "desc";
    }
}
