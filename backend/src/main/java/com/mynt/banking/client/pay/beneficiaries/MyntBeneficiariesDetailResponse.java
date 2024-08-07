package com.mynt.banking.client.pay.beneficiaries;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyntBeneficiariesDetailResponse {
    private List<MyntBeneficiaryDetail> beneficiaries;
    private Pagination pagination;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pagination {
        @JsonProperty("total_entries")
        private int totalEntries;
        @JsonProperty("total_pages")
        private int totalPages;
        @JsonProperty("current_page")
        private int currentPage;
        @JsonProperty("per_page")
        private int perPage;
        @JsonProperty("previous_page")
        private int previousPage;
        @JsonProperty("next_page")
        private int nextPage;
        @JsonProperty("order")
        private String order;
        @JsonProperty("order_asc_desc")
        private String orderAscDesc;
    }
}
