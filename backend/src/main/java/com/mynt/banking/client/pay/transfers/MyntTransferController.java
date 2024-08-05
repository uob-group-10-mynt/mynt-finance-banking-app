package com.mynt.banking.client.pay.transfers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.manage.balanaces.FindBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class MyntTransferController {

    private final MyntTransferService transferService;


    @GetMapping("/create/{email}")
    public ResponseEntity<JsonNode> create(@PathVariable(name = "email", required = true) String emailTransferTo,
                                                 @RequestParam(name = "currency", required = true) String currency,
                                                 @RequestParam(name = "amount", required = true) Double amount
                                           ) {
        return transferService.createTransfer(emailTransferTo,
                                                currency,
                                                amount);
    }

}