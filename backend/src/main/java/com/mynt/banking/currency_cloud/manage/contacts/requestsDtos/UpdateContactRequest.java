
package com.mynt.banking.currency_cloud.manage.contacts.requestsDtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Updates an existing contact. Returns the updated contact entity on success.")
public class UpdateContactRequest {
    
    //@NotNull
    @JsonProperty("first_name")
    @Size(max = 255)
    @Schema(description = "The contact's first name.",
            example = " ")
    @Builder.Default
    private String firstname = "";
    
    //@NotNull
    @JsonProperty("last_name")
    @Size(max = 255)
    @Schema(description = "The contact's last name.",
            example = " ")
    @Builder.Default
    private String lastname = "";
    
    //@NotNull
    @JsonProperty("email_address")
    @Size(max = 255)
    @Schema(description = "The contact's email address. A request to update a contact's email address does not immediately update the value. Instead, an email change request flow is initiated, sending notifications to both the old and new email addresses. The value will be updated when the change is confirmed via a link sent to the new email address.",
            example = " ")
    @Builder.Default
    private String emailAddress = "";
    
    //@NotNull
    @JsonProperty("your_reference")
    @Size(max = 255)
    @Schema(description = "User-generated reference code.",
            example = " ")
    @Builder.Default
    private String yourReference = "";
    
    //@NotNull
    @JsonProperty("phone_number")
    @Size(max = 255)
    @Schema(description = "The contact's phone number.",
            example = " ")
    @Builder.Default
    private String phoneNumber = "";
    
    //@NotNull
    @JsonProperty("mobile_phone_number")
    @Size(max = 255)
    @Schema(description = "The contact's mobile phone number.",
            example = " ")
    @Builder.Default
    private String mobilePhoneNumber = "";
    
    //@NotNull
    @JsonProperty("login_id")
    @Size(max = 255)
    @Schema(description = "The contact's Currencycloud login ID.",
            example = " ")
    @Builder.Default
    private String loginId = "";
    
    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Schema(description = "Contact status - \"enabled\" allows the contact to conduct activity on the sub-account, \"not_enabled\" disables the contact.",
            example = " ")
    @Builder.Default
    private String status = "";
    
    //@NotNull
    @JsonProperty("locale")
    @Size(max = 255)
    @Schema(description = "Locale code (\"en\", \"en-US\", \"fr-FR\").",
            example = " ")
    @Builder.Default
    private String locale = "";
    
    //@NotNull
    @JsonProperty("timezone")
    @Size(max = 255)
    @Schema(description = "Timezone (\"Europe/London\", \"America/New_York\").",
            example = " ")
    @Builder.Default
    private String timezone = "";
    
    //@NotNull
    @JsonProperty("date_of_birth")
    @Size(max = 255)
    @Schema(description = "Contact's date of birth. ISO 8601 format YYYY-MM-DD. Required if account type is individual.",
            example = " ")
    @Builder.Default
    private String dateOfBirth = "";
    
}