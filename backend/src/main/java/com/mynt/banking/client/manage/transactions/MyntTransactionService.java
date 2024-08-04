package com.mynt.banking.client.manage.transactions;


import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.transactions.TransactionService;
import com.mynt.banking.user.UserContextService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyntTransactionService {

    private final UserContextService userContextService;
    private final TransactionService transactionService;

    public TransactionResponse findTransaction(String currency, String relatedEntityType, Integer perPage, Integer page) {

        // Fetch Transactions
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.find(
                currency, relatedEntityType, userContextService.getCurrentUserUuid(), perPage, page);

        // Form TransactionResponse:
        JsonNode responseBody = currencyCloudTransactionResponse.getBody();
        List<TransactionResponse.Transaction> transactions = new ArrayList<>();
        if (responseBody == null) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactions(transactions);
            transactionResponse.setPagination(TransactionResponse.PaginationDTO.builder().build());
            return transactionResponse;
        }

        // Parse transactions
        JsonNode transactionsNode = responseBody.get("transactions");
        if (transactionsNode != null && transactionsNode.isArray()) {
            for (JsonNode transactionNode : transactionsNode) {
                TransactionResponse.Transaction transaction =
                    TransactionResponse.Transaction.builder()
                        .id(transactionNode.get("id").asText())
                        .accountId(transactionNode.get("account_id").asText())
                        .currency(transactionNode.get("currency").asText())
                        .amount(transactionNode.get("amount").asText())
                        .balanceAmount(transactionNode.get("balance_amount").asText())
                        .type(transactionNode.get("type").asText())
                        .relatedEntityType(transactionNode.get("related_entity_type").asText())
                        .relatedEntityId(transactionNode.get("related_entity_id").asText())
                        .relatedEntityShortReference(transactionNode.get("related_entity_short_reference").asText())
                        .status(transactionNode.get("status").asText())
                        .reason(transactionNode.has("reason") ? transactionNode.get("reason").asText() : null)
                        .createdAt(transactionNode.get("created_at").asText())
                        .action(transactionNode.get("action").asText())
                        .build();
                transactions.add(transaction);
            }
        }

        // Parse pagination
        JsonNode paginationNode = responseBody.get("pagination");
        TransactionResponse.PaginationDTO pagination;
        if (paginationNode != null) {
            pagination = TransactionResponse.PaginationDTO.builder()
                    .totalEntries(paginationNode.get("total_entries").asInt())
                    .totalPages(paginationNode.get("total_pages").asInt())
                    .currentPage(paginationNode.get("current_page").asInt())
                    .perPage(paginationNode.get("per_page").asInt())
                    .previousPage(paginationNode.get("previous_page").asInt())
                    .nextPage(paginationNode.get("next_page").asInt())
                    .order(paginationNode.get("order").asText())
                    .orderAscDesc(paginationNode.get("order_asc_desc").asText())
                    .build();
        } else {
            pagination = TransactionResponse.PaginationDTO.builder().build();
        }

        // Form TransactionResponse
        TransactionResponse transactionResponseDTO = new TransactionResponse();
        transactionResponseDTO.setTransactions(transactions);
        transactionResponseDTO.setPagination(pagination);

        return transactionResponseDTO;
    }

    public TransactionResponse.Transaction getTransaction(String transactionId) {
        // Fetch Transactions
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.get(
                transactionId,
                userContextService.getCurrentUserUuid());

        // Form TransactionResponse:
        JsonNode responseBody = currencyCloudTransactionResponse.getBody();

        // if body is empty --> return empty transaction:
        if (responseBody == null || responseBody.isEmpty()) { return TransactionResponse.Transaction.builder().build(); }

        // Extract transaction node
        JsonNode transactionNode = responseBody.get("transaction");

        // Build the transaction using the Builder pattern
        return TransactionResponse.Transaction.builder()
                .id(transactionNode.get("id").asText())
                .accountId(transactionNode.get("account_id").asText())
                .currency(transactionNode.get("currency").asText())
                .amount(transactionNode.get("amount").asText())
                .balanceAmount(transactionNode.get("balance_amount").asText())
                .type(transactionNode.get("type").asText())
                .relatedEntityType(transactionNode.get("related_entity_type").asText())
                .relatedEntityId(transactionNode.get("related_entity_id").asText())
                .relatedEntityShortReference(transactionNode.get("related_entity_short_reference").asText())
                .status(transactionNode.get("status").asText())
                .reason(transactionNode.has("reason") ? transactionNode.get("reason").asText() : null)
                .createdAt(transactionNode.get("created_at").asText())
                .action(transactionNode.get("action").asText())
                .build();
    }
}
