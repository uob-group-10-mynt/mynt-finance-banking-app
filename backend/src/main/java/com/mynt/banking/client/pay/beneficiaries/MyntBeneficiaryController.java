package com.mynt.banking.client.pay.beneficiaries;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/find/{id}")
    public BeneficiaryDetail findBeneficiary(@PathVariable String id) {
        return myntBeneficiaryService.getBeneficiary(id);
    }

    @PostMapping("/create")
    public BeneficiaryDetail create(@RequestBody BeneficiaryDetail request) {
        return myntBeneficiaryService.create(request);
    }

//    @DeleteMapping("/delete/{id}")
//    public delete(@PathVariable String id) {
//
//        return null;
//    }

}


// TODO: complete conversion detail
// TODO: pull James' code
// TODO: complete internal transfer detail --> show email and name
