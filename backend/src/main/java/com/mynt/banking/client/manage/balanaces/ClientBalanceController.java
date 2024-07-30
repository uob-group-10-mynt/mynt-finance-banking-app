package com.mynt.banking.client.manage.balanaces;

import com.mynt.banking.client.manage.balanaces.requests.ClientFindAllBalanceRequest;
import com.mynt.banking.currency_cloud.manage.balances.BalanceService;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalanceAllCurrencies;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/balances")
@RequiredArgsConstructor
public class ClientBalanceController {

    private final BalanceService balanceService;
    private final BalancesMapper balancesMapper;

    @PostMapping("/find")
    public ResponseEntity<?> findBalances(@RequestBody ClientFindAllBalanceRequest request) {
        FindBalanceAllCurrencies internalRequest = balancesMapper.toInternalRequest(request);

        return ResponseEntity.ok(balanceService.find(internalRequest));
    }
}


