package com.mynt.banking.currency_cloud.manage.contacts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FindContactRequest {

    @JsonProperty("account_name")
    @Size(max = 36)
    @Schema(description = "Account name", example = " ")
    @Builder.Default
    private String accountName = "";

    @JsonProperty("account_id")
    @Size(max = 36)
    @Schema(description = "Unique identifier for the account", example = " ")
    @Builder.Default
    private String accountId = "";

    @JsonProperty("first_name")
    @Size(max = 255)
    @Schema(description = "First name of the contact", example = " ")
    @Builder.Default
    private String firstName = "";

    @JsonProperty("last_name")
    @Size(max = 255)
    @Schema(description = "Last name of the contact", example = " ")
    @Builder.Default
    private String lastName = "";

    @JsonProperty("email_address")
    @Size(max = 255)
    @Schema(description = "Email address of the contact", example = " ")
    @Builder.Default
    private String emailAddress = "";

    @JsonProperty("phone_number")
    @Size(max = 20)
    @Schema(description = "Phone number of the contact", example = " ")
    @Builder.Default
    private String phoneNumber = "";

    @JsonProperty("your_reference")
    @Size(max = 255)
    @Schema(description = "Your reference for the contact", example = " ")
    @Builder.Default
    private String yourReference = "";

    @JsonProperty("login_id")
    @Size(max = 36)
    @Schema(description = "Login ID of the contact", example = " ")
    @Builder.Default
    private String loginId = "";

    @JsonProperty("status")
    @Size(max = 255)
    @Schema(description = "Status of the contact", example = " ")
    @Builder.Default
    private String status = "";

    @JsonProperty("locale")
    @Schema(description = "Locale code (\"en\", \"en-US\", \"fr-FR\").", example = " ")
    @Builder.Default
    private String locale = "";

    @JsonProperty("timezone")
    @Schema(description = "Timezone (\"Europe/London\", \"America/New_York\").", example = " ")
    @Builder.Default
    private String timezone = "";

    @JsonProperty("page")
    @Schema(description = "Page number", example = " ")
    @Builder.Default
    private String pageNumber = "";

    @JsonProperty("per_page")
    @Schema(description = "Number of results per page.", example = " ")
    @Builder.Default
    private String perPage = "";

    @JsonProperty("order")
    @Schema(description = "Any field name to change the sort order.", example = " ")
    @Builder.Default
    private String order = "";


    @JsonProperty("order_asc_desc")
    @Schema(description = """
                        Sort records in ascending or descending order.
                        
                        Enum:
                        \tasc
                        \tdesc
                        """, example = " ")
    @Builder.Default
    private String orderAscDesc = "";
}
