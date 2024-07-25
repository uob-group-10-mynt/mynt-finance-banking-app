package com.mynt.banking.currency_cloud.manage.accounts;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.accounts.requests.FindAccountRequest;
import com.mynt.banking.currency_cloud.manage.accounts.requests.UpdateAccountRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public Mono<ResponseEntity<JsonNode>> createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> findAccount(@RequestBody FindAccountRequest request) {
        return accountService.findAccount(request);
    }

    @PostMapping("/updateAccount/{id}")
    public Mono<ResponseEntity<JsonNode>> updateAccount(@RequestBody UpdateAccountRequest request,
                                                        @Schema(description = "Account UUID, returned by the create account endpoint.")
                                                        @PathVariable(name = "id") String id) {
        return accountService.updateAccount(request, id);
    }
}
