package com.mynt.banking.client.manage.balanaces.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientFindAllBalanceRequest {

    @Size(max = 255)
    @Schema(description = "Minimum balance amount.", example = "1000")
    private String amountFrom;

    @Size(max = 255)
    @Schema(description = "Maximum balance amount.", example = "5000")
    private String amountTo;

    @Schema(description = "Page number", example = "1")
    private String page;

    @Schema(description = "Number of results per page.", example = "10")
    private String perPage;

    @Schema(description = "A field name to change the sort order - \"created_at\", \"amount\", \"updated_at\" or \"currency\".", example = "amount")
    private String order;
}
