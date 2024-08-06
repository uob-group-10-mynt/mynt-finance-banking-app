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

@Component
@RequiredArgsConstructor
public class MyntTransactionService {

    private final UserContextService userContextService;
    private final TransactionService transactionService;
    private final FundingService fundingService;
    private final PaymentService paymentService;
    private final BeneficiaryService beneficiaryService;

    public TransactionsDetailResponse find(String currency, String relatedEntityType, Integer perPage, Integer page) {
        // Fetch Transactions
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.find(
                currency, relatedEntityType, userContextService.getCurrentUserUuid(), perPage, page);

        // Form TransactionDetailResponse:
        JsonNode responseBody = currencyCloudTransactionResponse.getBody();
        if (responseBody == null ||  responseBody.path("total_entries").asInt() == 0) {
            throw new CurrencyCloudException("No content", HttpStatus.NO_CONTENT);
        }

        // Parse transactions
        List<TransactionsDetailResponse.Transaction> transactions = new ArrayList<>();
        JsonNode transactionsNode = responseBody.get("transactions");
        if (transactionsNode != null && transactionsNode.isArray()) {
            for (JsonNode transactionNode : transactionsNode) {
                TransactionsDetailResponse.Transaction transaction =
                    TransactionsDetailResponse.Transaction.builder()
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
        TransactionsDetailResponse.Pagination.PaginationBuilder pagination = TransactionsDetailResponse.Pagination.builder();
        if (paginationNode != null) {
            pagination.totalEntries(paginationNode.get("total_entries").asInt())
                .totalPages(paginationNode.get("total_pages").asInt())
                .currentPage(paginationNode.get("current_page").asInt())
                .perPage(paginationNode.get("per_page").asInt())
                .previousPage(paginationNode.get("previous_page").asInt())
                .nextPage(paginationNode.get("next_page").asInt())
                .order(paginationNode.get("order").asText())
                .orderAscDesc(paginationNode.get("order_asc_desc").asText());
        }

        // Form TransactionDetailResponse
        TransactionsDetailResponse transactionDetailResponseDTO = new TransactionsDetailResponse();
        transactionDetailResponseDTO.setTransactions(transactions);
        transactionDetailResponseDTO.setPagination(pagination.build());
        return transactionDetailResponseDTO;
    }

    private TransactionsDetailResponse.Transaction get(String transactionId) {
        // Fetch Transactions
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.get(
                transactionId,
                userContextService.getCurrentUserUuid());

        // Form TransactionDetailResponse:
        JsonNode transactionDetail = currencyCloudTransactionResponse.getBody();

        // if body is empty --> return empty transaction:
        if (transactionDetail == null || transactionDetail.isEmpty()) {
            throw new CurrencyCloudException("No content", HttpStatus.NO_CONTENT);
        }

        // Build the transaction using the Builder pattern
        return TransactionsDetailResponse.Transaction.builder()
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
        TransactionsDetailResponse.Transaction transactionDetail = get(transactionID);

        // Ensure Transaction Detail is not empty:
        if (transactionDetail == null) {
            throw new CurrencyCloudException("No content", HttpStatus.NO_CONTENT);
        }

        // Extract key fields for following request:
        String accountID = transactionDetail.getAccountId();
        String relatedEntityType = transactionDetail.getRelatedEntityType();
        String relatedEntityId = transactionDetail.getRelatedEntityId();
        if (accountID == null || accountID.isBlank() || accountID.isEmpty()
            || !relatedEntityType.equals("payment") || relatedEntityId == null
            || relatedEntityId.isEmpty() || relatedEntityType.isBlank()) {
            throw new CurrencyCloudException("Currency Cloud Error: Missing necessary fields.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Fetch Account Details:
        FindAccountDetailsRequest findAccountDetailsRequest = FindAccountDetailsRequest.builder()
                .accountId(accountID)
                .currency(transactionDetail.getCurrency())
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .paymentType("priority")
                .build();
        ResponseEntity<JsonNode> findAccountDetailResponse = fundingService.findAccountDetails(findAccountDetailsRequest);


        if (findAccountDetailResponse.getBody() == null ||
                findAccountDetailResponse.getBody().get("funding_accounts") == null) {
            throw new CurrencyCloudException("No content", HttpStatus.NO_CONTENT);
        }

        JsonNode accountDetails = (findAccountDetailResponse.getBody()).get("funding_accounts");
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
