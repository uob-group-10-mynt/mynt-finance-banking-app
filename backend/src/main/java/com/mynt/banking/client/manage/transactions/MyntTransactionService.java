package com.mynt.banking.client.manage.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mynt.banking.currency_cloud.collect.funding.CurrencyCloudFundingService;
import com.mynt.banking.currency_cloud.collect.funding.requests.CurrencyCloudFindFundingAccountsRequest;
import com.mynt.banking.currency_cloud.convert.conversions.CurrencyCloudConversionsService;
import com.mynt.banking.currency_cloud.convert.conversions.requests.CurrencyCloudGetConversionRequest;
import com.mynt.banking.currency_cloud.manage.transactions.CurrencyCloudTransactionsService;
import com.mynt.banking.currency_cloud.manage.transactions.requests.CurrencyCloudFindTransactionsRequest;
import com.mynt.banking.currency_cloud.manage.transactions.requests.CurrencyCloudGetTransactionRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.CurrencyCloudBeneficiariesService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.CurrencyCloudGetBeneficiaryRequest;
import com.mynt.banking.currency_cloud.pay.payments.CurrencyCloudPaymentsService;
import com.mynt.banking.currency_cloud.pay.payments.requests.CurrencyCloudGetPaymentRequest;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyntTransactionService {

    private final UserContextService userContextService;
    private final CurrencyCloudTransactionsService transactionService;
    private final CurrencyCloudFundingService fundingService;
    private final CurrencyCloudPaymentsService paymentService;
    private final CurrencyCloudBeneficiariesService beneficiaryService;
    private final CurrencyCloudConversionsService conversionService;

    public MyntTransactionsDetailResponse find(String currency, String relatedEntityType, Integer perPage, Integer page) {
        // Fetch Transactions
        CurrencyCloudFindTransactionsRequest findTransactionsRequest = CurrencyCloudFindTransactionsRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .currency(currency)
                .relatedEntityType(relatedEntityType)
                .perPage(String.valueOf(perPage))
                .page(String.valueOf(page))
                .build();
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.findTransactions(findTransactionsRequest);

        // Form TransactionDetailResponse:
        JsonNode responseBody = currencyCloudTransactionResponse.getBody();
        Optional.ofNullable(responseBody)
                .map(body -> body.path("pagination"))
                .map(pagination -> pagination.path("total_entries").asInt())
                .filter(entries -> entries > 0)
                .orElseThrow(() -> new CurrencyCloudException("No content", HttpStatus.NO_CONTENT));

        // Parse transactions
        List<MyntTransactionsDetailResponse.Transaction> transactions = new ArrayList<>();
        JsonNode transactionsNode = responseBody.get("transactions");
        if (transactionsNode != null && transactionsNode.isArray()) {
            for (JsonNode transactionNode : transactionsNode) {
                MyntTransactionsDetailResponse.Transaction transaction =
                        MyntTransactionsDetailResponse.Transaction.builder()
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
        MyntTransactionsDetailResponse.Pagination.PaginationBuilder pagination = MyntTransactionsDetailResponse.Pagination.builder();
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
        MyntTransactionsDetailResponse transactionDetailResponseDTO = new MyntTransactionsDetailResponse();
        transactionDetailResponseDTO.setTransactions(transactions);
        transactionDetailResponseDTO.setPagination(pagination.build());
        return transactionDetailResponseDTO;
    }

    private MyntTransactionsDetailResponse.Transaction get(String transactionId) {
        // Fetch Transactions
        CurrencyCloudGetTransactionRequest getTransactionRequest = CurrencyCloudGetTransactionRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .build();
        ResponseEntity<JsonNode> currencyCloudTransactionResponse = transactionService.getTransaction(
                transactionId,
                getTransactionRequest);

        // Form TransactionDetailResponse:
        JsonNode transactionDetail = currencyCloudTransactionResponse.getBody();
        if (transactionDetail == null || transactionDetail.isEmpty()) {
            throw new CurrencyCloudException("No content", HttpStatus.NO_CONTENT);
        }

        // Build the transaction using the Builder pattern
        return MyntTransactionsDetailResponse.Transaction.builder()
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

    public MyntPaymentDetailResponse getPaymentDetail(String transactionID) {
        // Fetch transaction and extract key fields for following request:
        MyntTransactionsDetailResponse.Transaction transactionDetail = get(transactionID);
        String accountID = transactionDetail.getAccountId();
        String relatedEntityType = transactionDetail.getRelatedEntityType();
        String relatedEntityId = transactionDetail.getRelatedEntityId();

        if (accountID == null || accountID.isBlank() || !relatedEntityType.equals("payment") ||
                relatedEntityId == null || relatedEntityId.isBlank()) {
            throw new CurrencyCloudException("Currency Cloud Error: Missing necessary fields.",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Fetch Account Details:
        CurrencyCloudFindFundingAccountsRequest findAccountDetailsRequest = CurrencyCloudFindFundingAccountsRequest.builder()
                .accountId(accountID)
                .currency(transactionDetail.getCurrency())
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .paymentType("priority")
                .build();

        ResponseEntity<JsonNode> findAccountDetailResponse = fundingService.findFundingAccounts(findAccountDetailsRequest);
        JsonNode accountDetails = Optional.ofNullable(findAccountDetailResponse.getBody())
                .map(body -> body.get("funding_accounts"))
                .filter(node -> !node.isEmpty() && node.size() == 1)
                .orElseThrow(() -> new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.",
                        HttpStatus.UNPROCESSABLE_ENTITY));

        // Fill out PaymentDetailResponse.PayerAccountDetail:
        MyntPaymentDetailResponse.PayerAccountDetails payerAccountDetails = MyntPaymentDetailResponse.PayerAccountDetails.builder()
                .accountHolderName(accountDetails.get(0).path("account_holder_name").asText())
                .bankCountry(accountDetails.get(0).path("bank_country").asText())
                .bankName(accountDetails.get(0).path("bank_name").asText())
                .accountNumberType(accountDetails.get(0).path("account_number_type").asText())
                .accountNumber(accountDetails.get(0).path("account_number").asText())
                .routingCodeType(accountDetails.get(0).path("routing_code_type").asText())
                .routingCode(accountDetails.get(0).path("routing_code").asText())
                .build();

        // Fetch Payment details using related_entity_id:
        CurrencyCloudGetPaymentRequest getPaymentRequest = CurrencyCloudGetPaymentRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .build();
        ResponseEntity<JsonNode> paymentDetailsResponse = paymentService.getPayment(relatedEntityId, getPaymentRequest);

        JsonNode paymentDetails = Optional.ofNullable(paymentDetailsResponse.getBody())
                .filter(node -> !node.isEmpty())
                .orElseThrow(() -> new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.",
                        HttpStatus.UNPROCESSABLE_ENTITY));

        // Fetch Beneficiary Details:
        String beneficiaryId = Optional.ofNullable(paymentDetails.path("beneficiary_id"))
                .filter(JsonNode::isValueNode)
                .map(JsonNode::asText)
                .orElseThrow(() -> new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.",
                        HttpStatus.UNPROCESSABLE_ENTITY));

        CurrencyCloudGetBeneficiaryRequest getBeneficiaryRequest = CurrencyCloudGetBeneficiaryRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .build();
        ResponseEntity<JsonNode> beneficiaryDetailsResponse = beneficiaryService.getBeneficiary(beneficiaryId,
                getBeneficiaryRequest);

        JsonNode beneficiaryDetails = Optional.ofNullable(beneficiaryDetailsResponse.getBody())
                .filter(node -> !node.isEmpty())
                .orElseThrow(() -> new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.",
                        HttpStatus.UNPROCESSABLE_ENTITY));

        MyntPaymentDetailResponse.BeneficiaryAccountDetails beneficiaryAccountDetails = MyntPaymentDetailResponse.BeneficiaryAccountDetails
                .builder()
                .accountHolderName(beneficiaryDetails.path("bank_account_holder_name").asText())
                .bankCountry(beneficiaryDetails.path("bank_country").asText())
                .bankName(beneficiaryDetails.path("bank_name").asText())
                .iban(beneficiaryDetails.path("iban").asText())
                .bicSwift(beneficiaryDetails.path("bic_swift").asText())
                .build();

        return MyntPaymentDetailResponse.builder()
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

    public MyntConversionDetailResponse getConversionDetail(String id) {
        // Fetch transaction and extract key fields for following request:
        MyntTransactionsDetailResponse.Transaction transactionDetail = get(id);
        String relatedEntityType = transactionDetail.getRelatedEntityType();
        String relatedEntityId = transactionDetail.getRelatedEntityId();

        if (!relatedEntityType.equals("conversion") || relatedEntityId == null || relatedEntityId.isBlank()) {
            throw new CurrencyCloudException("Currency Cloud Error: Missing necessary fields.",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Fetch Payment details using related_entity_id:
        CurrencyCloudGetConversionRequest getConversionRequest = CurrencyCloudGetConversionRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .build();
        ResponseEntity<JsonNode> conversionDetailResponse = conversionService.getConversion(
                relatedEntityId, getConversionRequest);

        JsonNode conversionDetails = Optional.ofNullable(conversionDetailResponse.getBody())
                .filter(node -> !node.isEmpty())
                .orElseThrow(() -> new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.",
                        HttpStatus.UNPROCESSABLE_ENTITY));
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.treeToValue(conversionDetails, MyntConversionDetailResponse.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to Conversion Detail Response",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

