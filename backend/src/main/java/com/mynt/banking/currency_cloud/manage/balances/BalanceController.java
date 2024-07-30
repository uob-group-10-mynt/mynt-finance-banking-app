package com.mynt.banking.currency_cloud.manage.balances;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalanceAllCurrencies;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalancesRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/balances")
@RequiredArgsConstructor
public class BalanceController {

    private static final Logger log = LoggerFactory.getLogger(BalanceController.class);
    private final BalanceService balanceService;

    @Secured("ROLE_USER")
    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> find(FindBalanceAllCurrencies request) {
        // Check if the SecurityContext has the required authority
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.debug("Authentication object: {}", auth);
            log.debug("Principal: {}", auth.getPrincipal());
            log.debug("Authorities: {}", auth.getAuthorities());
        } else {
            log.warn("Authentication object is null");
        }
        return balanceService.find(request);
    }

    @PostMapping("/find/{currencyCode}/")
    public Mono<ResponseEntity<JsonNode>> find( @Schema(description = "Three-letter ISO currency code.")
                                                @PathVariable(name = "currencyCode") String currencyCode ,
                                                FindBalancesRequest request) {
        return balanceService.findForParticularCurrency(request, currencyCode);
    }
}
