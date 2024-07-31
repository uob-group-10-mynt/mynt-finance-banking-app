package com.mynt.banking.currency_cloud.manage.reference.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetPayerRequirementsRequest {

    @JsonProperty("payer_country")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Two-letter ISO country code.", example = "gb")
    @Builder.Default
    private String payerCountry = "gb";

    @JsonProperty("payer_entity_type")
    @Size(max = 255)
    @Schema(description = "Legal entity type - company or individual.", example = " ")
    @Builder.Default
    private String payerEntityType = "";

    @JsonProperty("payment_type")
    @Size(max = 255)
    @Schema(description = "Currencycloud supports two types of payments: \"priority\" (Swift), made using the Swift network; and \"regular\" (local), made using the local bank network.", example = " ")
    @Builder.Default
    private String paymentType = "";

    @JsonProperty("currency")
    @Size(max = 255)
    @Schema(description = "Three-letter ISO 4217 currency code.", example = " ")
    @Builder.Default
    private String currency = "";

}
