package com.mynt.banking.client.pay.transfers;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class MyntTransferController {

    private final MyntTransferService transferService;

    @PostMapping("/create")
    public ResponseEntity<JsonNode> create(@RequestParam(name = "email", required = true) String emailTransferTo,
                                                 @RequestParam(name = "currency", required = true) String currency,
                                                 @RequestParam(name = "amount", required = true) Double amount
                                           ) {
        return transferService.createTransfer(emailTransferTo, currency, amount);
    }
}