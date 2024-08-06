package com.mynt.banking.client.pay.beneficiaries;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/beneficiary")
@RequiredArgsConstructor
public class MyntBeneficiaryController {

    private final MyntBeneficiaryService myntBeneficiaryService;

    @GetMapping
    public BeneficiariesDetailResponse find(
                        @RequestParam(required = false, name = "per_page") Integer perPage,
                        @RequestParam(required = false, name = "page") Integer page) {
        return myntBeneficiaryService.find(perPage, page);
    }

    @GetMapping("/{id}")
    public BeneficiaryDetail get(@PathVariable("id") String id) {
        return myntBeneficiaryService.get(id);
    }

    @PostMapping("/create")
    public BeneficiaryDetail create(@RequestBody BeneficiaryDetail request) {
        return myntBeneficiaryService.create(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        return myntBeneficiaryService.delete(id);
    }

}


// TODO: complete conversion detail and restructure endpoint to be transactions/{id}
// TODO: Accept PR from James' code
// TODO: complete internal transfer detail --> show email and name
// TODO: retry and rate limiting
