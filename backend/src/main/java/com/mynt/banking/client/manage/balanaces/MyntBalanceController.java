package com.mynt.banking.client.manage.balanaces;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
public class MyntBalanceController {

    private final MyntBalanceService balanceService;

    // find a single balance by currency code:
    @GetMapping("/{currency_code}")
    public FindBalanceResponse findBalance(@PathVariable("currency_code") String currency) {
        return balanceService.findBalance(currency);
    }

    // find all balances associated with sub-account:
    @GetMapping
    public List<FindBalanceResponse> findBalances() { return balanceService.findBalances(); }
}