package com.mynt.banking.client.manage.transactions;


import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.transactions.TransactionService;
import com.mynt.banking.user.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
        TransactionResponse.PaginationDTO pagination = new TransactionResponse.PaginationDTO();
        if (responseBody == null) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactions(transactions);
            transactionResponse.setPagination(pagination);
            return transactionResponse;
        }

        // Parse transactions
        JsonNode transactionsNode = responseBody.get("transactions");
        if (transactionsNode != null && transactionsNode.isArray()) {
            for (JsonNode transactionNode : transactionsNode) {
                TransactionResponse.Transaction transaction = new TransactionResponse.Transaction();
                transaction.setId(transactionNode.get("id").asText());
                transaction.setAccountId(transactionNode.get("account_id").asText());
                transaction.setCurrency(transactionNode.get("currency").asText());
                transaction.setAmount(transactionNode.get("amount").asText());
                transaction.setBalanceAmount(transactionNode.get("balance_amount").asText());
                transaction.setType(transactionNode.get("type").asText());
                transaction.setRelatedEntityType(transactionNode.get("related_entity_type").asText());
                transaction.setRelatedEntityId(transactionNode.get("related_entity_id").asText());
                transaction.setRelatedEntityShortReference(transactionNode.get("related_entity_short_reference").asText());
                transaction.setStatus(transactionNode.get("status").asText());
                transaction.setReason(transactionNode.has("reason") ? transactionNode.get("reason").asText() : null);
                transaction.setCreatedAt(transactionNode.get("created_at").asText());
                transaction.setAction(transactionNode.get("action").asText());
                transactions.add(transaction);
            }
        }

        // Parse pagination
        JsonNode paginationNode = responseBody.get("pagination");
        if (paginationNode != null) {
            pagination.setTotalEntries(paginationNode.get("total_entries").asInt());
            pagination.setTotalPages(paginationNode.get("total_pages").asInt());
            pagination.setCurrentPage(paginationNode.get("current_page").asInt());
            pagination.setPerPage(paginationNode.get("per_page").asInt());
            pagination.setPreviousPage(paginationNode.get("previous_page").asInt());
            pagination.setNextPage(paginationNode.get("next_page").asInt());
            pagination.setOrder(paginationNode.get("order").asText());
            pagination.setOrderAscDesc(paginationNode.get("order_asc_desc").asText());
        }

        // Form TransactionResponse
        TransactionResponse transactionResponseDTO = new TransactionResponse();
        transactionResponseDTO.setTransactions(transactions);
        transactionResponseDTO.setPagination(pagination);

        return transactionResponseDTO;
    }
}
