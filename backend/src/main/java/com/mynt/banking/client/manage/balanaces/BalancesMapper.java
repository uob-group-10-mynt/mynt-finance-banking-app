package com.mynt.banking.client.manage.balanaces;

import com.mynt.banking.auth.JwtUserDetails;
import com.mynt.banking.client.manage.balanaces.requests.ClientFindAllBalanceRequest;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalanceAllCurrencies;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalancesMapper {

    public FindBalanceAllCurrencies toInternalRequest(@NotNull ClientFindAllBalanceRequest clientRequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();

        return FindBalanceAllCurrencies.builder()
                .onBehalfOf(principal.getUuid())
                .amountFrom(clientRequest.getAmountFrom())
                .amountTo(clientRequest.getAmountTo())
                .page(clientRequest.getPage())
                .perPage(clientRequest.getPerPage())
                .order(clientRequest.getOrder())
                .build();
    }
}
