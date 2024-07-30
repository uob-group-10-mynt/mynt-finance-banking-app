package com.mynt.banking.mPesa.flutterwave.requests;

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
@Schema(description = "CreateTransactionRequest")
public class MPesaToFlutterWearDto {


    @NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Schema(description = "Amount", example = "1")
    @Builder.Default
    private String amount = "1" ;

    @NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Schema(description = "currency", example = "KES")
    @Builder.Default
    private String currency = "KES" ;

    @NotNull
    @JsonProperty("email")
    @Size(max = 255)
    @Schema(description = "email", example = "james@jameslove.com")
    @Builder.Default
    private String email = "james@jameslove.com" ;

    @NotNull
    @JsonProperty("phone_number")
    @Size(max = 255)
    @Schema(description = "phone_number", example = "+44 7824792472")
    @Builder.Default
    private String phone_number = "0447824792472" ;

    @NotNull
    @JsonProperty("fullname")
    @Size(max = 255)
    @Schema(description = "fullname", example = "James Love")
    @Builder.Default
    private String fullname = "James Love" ;

}
