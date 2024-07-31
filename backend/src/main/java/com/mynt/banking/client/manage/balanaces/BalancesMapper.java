package com.mynt.banking.client.manage.balanaces;

import com.mynt.banking.client.manage.balanaces.requests.ClientFindAllBalanceRequest;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalanceAllCurrencies;
import com.mynt.banking.user.UserContextService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalancesMapper {

    private final UserContextService userContextService;

    public FindBalanceAllCurrencies toInternalRequest(@NotNull ClientFindAllBalanceRequest clientRequest) {

        return FindBalanceAllCurrencies.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .amountFrom(clientRequest.getAmountFrom())
                .amountTo(clientRequest.getAmountTo())
                .page(clientRequest.getPage())
                .perPage(clientRequest.getPerPage())
                .order(clientRequest.getOrder())
                .build();
    }
}
