package com.mynt.banking.client.pay.beneficiaries;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/beneficiary")
@RequiredArgsConstructor
public class MyntBeneficiaryController {

    private final MyntBeneficiaryService myntBeneficiaryService;

    @GetMapping
    public BeneficiariesDetailResponse findBeneficiaries(
                        @RequestParam(required = false, name = "per_page") Integer perPage,
                        @RequestParam(required = false, name = "page") Integer page) {
        return myntBeneficiaryService.findBeneficiaries(perPage, page);
    }

    @GetMapping("/find/{id}")
    public BeneficiariesDetailResponse.Beneficiary findBeneficiary(@PathVariable String id) {
        return null;
    }

}


// TODO: add bank_brand_label = mynt to dtos agaon ask GunHo
// TODO: complete beneficiary wrappers and error handling
// TODO: complete conversion detail
// TODO: complete internal transfer detail --> show email and name
