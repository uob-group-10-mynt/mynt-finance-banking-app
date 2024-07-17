package com.mynt.banking.currency_cloud.manage.contacts.requestsDtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateContact {

    @JsonProperty("account_id")
    @NotNull
    @Size(max = 36)
    @Schema(description = "Account UUID", example = "53d15949-b1e9-4335-a4e4-56ae8adef95e")
    @Builder.Default
    private String accountId = "53d15949-b1e9-4335-a4e4-56ae8adef95e";

    @JsonProperty("first_name")
    @NotNull
    @Size(max = 255)
    @Schema(description = "The contact's first name.", example = "James")
    @Builder.Default
    private String firstName = "James";

    @JsonProperty("last_name")
    @NotNull
    @Size(max = 255)
    @Schema(description = "The contact's last name.", example = "Love")
    @Builder.Default
    private String lastName = "Love";

    @JsonProperty("email_address")
    @NotNull
    @Email
    @Size(max = 255)
    @Schema(description = "The contact's email address.", example = "john.doe@example.com")
    @Builder.Default
    private String emailAddress = "john.doe@example.com";

    @JsonProperty("phone_number")
    @NotNull
    @Size(max = 50)
    @Schema(description = "The contact's phone number.", example = "+44 1234 567890")
    @Builder.Default
    private String phoneNumber = "+44 1234 567890";

    @JsonProperty("your_reference")
    @Size(max = 255)
    @Schema(description = "User-generated reference code.", example = " ")
    @Builder.Default
    private String yourReference = "";

    @JsonProperty("mobile_phone_number")
    @Size(max = 50)
    @Schema(description = "The contact's mobile phone number.", example = " ")
    @Builder.Default
    private String mobilePhoneNumber = "";

    @JsonProperty("login_id")
    @Size(max = 255)
    @Schema(description = "The contact's Currencycloud login ID, allows the contact to login and conduct activity on the sub-account.", example = " ")
    @Builder.Default
    private String loginId = "";

    @JsonProperty("status")
    @Size(max = 255)
    @Schema(description = """
            Contact status - "enabled" allows the contact to conduct activity on the sub-account, "not_enabled" disables the contact.\n
            Enum:
            \tenabled
            \tnot_enabled
            """, example = " ")
    @Builder.Default
    private String status = "enabled";

    @JsonProperty("locale")
    @Size(max = 5)
    @Schema(description = "Locale code (\"en\", \"en-US\", \"fr-FR\").", example = " ")
    @Builder.Default
    private String locale = "";

    @JsonProperty("timezone")
    @Size(max = 255)
    @Schema(description = "Timezone (\"Europe/London\", \"America/New_York\").", example = " ")
    @Builder.Default
    private String timezone = "";

    @JsonProperty("date_of_birth")
    @NotNull
    @Size(max = 10)
    @Schema(description = """
            The contact's date of birth.
            ISO 8601 format YYYY-MM-DD.
            Required if account type is individual.""", example = "1997-08-16")
    @Builder.Default
    private String dateOfBirth = "1997-08-16";

}
