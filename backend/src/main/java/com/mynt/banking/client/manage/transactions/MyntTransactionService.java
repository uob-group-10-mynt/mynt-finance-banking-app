package com.mynt.banking.client.manage.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.collect.funding.FindAccountDetailsRequest;
import com.mynt.banking.currency_cloud.collect.funding.FundingService;
import com.mynt.banking.currency_cloud.manage.transactions.TransactionService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.BeneficiaryService;
import com.mynt.banking.currency_cloud.pay.payments.PaymentService;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MyntTransactionService {

    private final UserContextService userContextService;
    private final TransactionService transactionService;
    private final FundingService fundingService;
    private final PaymentService paymentService;
    private final BeneficiaryService beneficiaryService;

    public TransactionDetailResponse findTransaction(String currency, String relatedEntityType, Integer perPage, Integer page) {
        // Fetch Transactions
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.find(
                currency, relatedEntityType, userContextService.getCurrentUserUuid(), perPage, page);

        // Form TransactionDetailResponse:
        JsonNode responseBody = currencyCloudTransactionResponse.getBody();
        List<TransactionDetailResponse.Transaction> transactions = new ArrayList<>();
        if (responseBody == null) {
            TransactionDetailResponse transactionDetailResponse = new TransactionDetailResponse();
            transactionDetailResponse.setTransactions(transactions);
            transactionDetailResponse.setPagination(TransactionDetailResponse.Pagination.builder().build());
            return transactionDetailResponse;
        }

        // Parse transactions
        JsonNode transactionsNode = responseBody.get("transactions");
        if (transactionsNode != null && transactionsNode.isArray()) {
            for (JsonNode transactionNode : transactionsNode) {
                TransactionDetailResponse.Transaction transaction =
                    TransactionDetailResponse.Transaction.builder()
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
        TransactionDetailResponse.Pagination pagination;
        if (paginationNode != null) {
            pagination = TransactionDetailResponse.Pagination.builder()
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
            pagination = TransactionDetailResponse.Pagination.builder().build();
        }

        // Form TransactionDetailResponse
        TransactionDetailResponse transactionDetailResponseDTO = new TransactionDetailResponse();
        transactionDetailResponseDTO.setTransactions(transactions);
        transactionDetailResponseDTO.setPagination(pagination);

        return transactionDetailResponseDTO;
    }

    private TransactionDetailResponse.Transaction getTransaction(String transactionId) {
        // Fetch Transactions
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.get(
                transactionId,
                userContextService.getCurrentUserUuid());

        // Form TransactionDetailResponse:
        JsonNode transactionDetail = currencyCloudTransactionResponse.getBody();

        // if body is empty --> return empty transaction:
        if (transactionDetail == null || transactionDetail.isEmpty()) { return TransactionDetailResponse.Transaction.builder().build(); }

        // Build the transaction using the Builder pattern
        return TransactionDetailResponse.Transaction.builder()
                .id(transactionDetail.get("id").asText())
                .accountId(transactionDetail.get("account_id").asText())
                .currency(transactionDetail.get("currency").asText())
                .amount(transactionDetail.get("amount").asText())
                .balanceAmount(transactionDetail.get("balance_amount").asText())
                .type(transactionDetail.get("type").asText())
                .relatedEntityType(transactionDetail.get("related_entity_type").asText())
                .relatedEntityId(transactionDetail.get("related_entity_id").asText())
                .relatedEntityShortReference(transactionDetail.get("related_entity_short_reference").asText())
                .status(transactionDetail.get("status").asText())
                .reason(transactionDetail.has("reason") ? transactionDetail.get("reason").asText() : null)
                .createdAt(transactionDetail.get("created_at").asText())
                .action(transactionDetail.get("action").asText())
                .build();
    }

    public PaymentDetailResponse getPaymentDetail(String transactionID) {

        // Fetch transaction:
        TransactionDetailResponse.Transaction transactionDetail = getTransaction(transactionID);

        // Ensure Transaction Detail is not empty:
        if (transactionDetail == null) { return PaymentDetailResponse.builder().build(); }

        // Extract key fields for following request:
        String accountID = transactionDetail.getAccountId();
        String relatedEntityType = transactionDetail.getRelatedEntityType();
        String relatedEntityId = transactionDetail.getRelatedEntityId();
        if (accountID == null || accountID.isBlank() || accountID.isEmpty()
            || !relatedEntityType.equals("payment") || relatedEntityId == null
            || relatedEntityId.isEmpty() || relatedEntityType.isBlank()) {
            throw new CurrencyCloudException("Currency Cloud Error: Missing necessary fields missing.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Fetch Account Details:
        FindAccountDetailsRequest findAccountDetailsRequest = FindAccountDetailsRequest.builder()
                .accountId(accountID)
                .currency(transactionDetail.getCurrency())
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .paymentType("priority")
                .build();
        ResponseEntity<JsonNode> findAccountDetailResponse = fundingService.findAccountDetails(findAccountDetailsRequest);

        JsonNode accountDetails = Objects.requireNonNull(findAccountDetailResponse.getBody()).get("funding_accounts");

        if (accountDetails == null || accountDetails.isEmpty() || accountDetails.size() != 1) {
            throw new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Fill out PaymentDetailResponse.PayerAccountDetail:
        PaymentDetailResponse.PayerAccountDetails payerAccountDetails = PaymentDetailResponse.PayerAccountDetails.builder()
                .accountHolderName(accountDetails.get(0).path("account_holder_name").asText())
                .bankCountry(accountDetails.get(0).path("bank_country").asText())
                .bankName(accountDetails.get(0).path("bank_name").asText())
                .accountNumberType(accountDetails.get(0).path("account_number_type").asText())
                .accountNumber(accountDetails.get(0).path("account_number").asText())
                .routingCodeType(accountDetails.get(0).path("routing_code_type").asText())
                .routingCode(accountDetails.get(0).path("routing_code").asText())
                .build();

        // Fetch Payment details using related_entity_id:
        ResponseEntity<JsonNode> paymentDetailsResponse = paymentService.get(relatedEntityId, userContextService.getCurrentUserUuid());
        JsonNode paymentDetails = paymentDetailsResponse.getBody();
        if (paymentDetails == null || paymentDetails.isEmpty()) {
            throw new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // TODO add beneficiary to my cc account and make a payment for testing // check null beneficiary id first:

        // Fetch Beneficiary Details:
        if (paymentDetails.path("beneficiary_id").isNull()) {
            throw new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        ResponseEntity<JsonNode> beneficiaryDetailsResponse = beneficiaryService.get(
                paymentDetails.path("beneficiary_id").asText(), userContextService.getCurrentUserUuid());
        JsonNode beneficiaryDetails = beneficiaryDetailsResponse.getBody();
        if (beneficiaryDetails == null || beneficiaryDetails.isEmpty()) {
            throw new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        PaymentDetailResponse.BeneficiaryAccountDetails beneficiaryAccountDetails = PaymentDetailResponse.BeneficiaryAccountDetails
                .builder()
                .accountHolderName(beneficiaryDetails.path("bank_account_holder_name").asText())
                .bankCountry(beneficiaryDetails.path("bank_country").asText())
                .bankName(beneficiaryDetails.path("bank_name").asText())
                .iban(beneficiaryDetails.path("iban").asText())
                .bicSwift(beneficiaryDetails.path("bic_swift").asText())
                .build();

        return PaymentDetailResponse.builder()
                    .id(transactionID)
                    .amount(paymentDetails.path("amount").asText())
                    .currency(paymentDetails.path("currency").asText())
                    .beneficiaryAccountDetails(beneficiaryAccountDetails)
                    .payerAccountDetails(payerAccountDetails)
                    .reference(paymentDetails.path("reference").asText())
                    .reason(paymentDetails.path("reason").asText())
                    .status(paymentDetails.path("status").asText())
                    .paymentType(paymentDetails.path("payment_type").asText())
                    .paymentDate(paymentDetails.path("payment_date").asText())
                    .shortReference(paymentDetails.path("short_reference").asText())
                    .createdAt(paymentDetails.path("created_at").asText())
                    .reviewStatus(paymentDetails.path("review_status").asText())
                    .build();
    }
}
