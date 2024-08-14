package com.mynt.banking.currency_cloud.manage.contacts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateContactRequest {

    //@NotNull
    @JsonProperty("first_name")
    @Size(max = 255)
    @Builder.Default
    private String firstName = "";

    //@NotNull
    @JsonProperty("last_name")
    @Size(max = 255)
    @Builder.Default
    private String lastName = "";

    //@NotNull
    @JsonProperty("email_address")
    @Size(max = 255)
    @Builder.Default
    private String emailAddress = "";

    //@NotNull
    @JsonProperty("your_reference")
    @Size(max = 255)
    @Builder.Default
    private String yourReference = "";

    //@NotNull
    @JsonProperty("phone_number")
    @Size(max = 255)
    @Builder.Default
    private String phoneNumber = "";

    //@NotNull
    @JsonProperty("mobile_phone_number")
    @Size(max = 255)
    @Builder.Default
    private String mobilePhoneNumber = "";

    //@NotNull
    @JsonProperty("login_id")
    @Size(max = 255)
    @Builder.Default
    private String loginId = "";

    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    private String status = "";

    //@NotNull
    @JsonProperty("locale")
    @Size(max = 255)
    @Builder.Default
    private String locale = "";

    //@NotNull
    @JsonProperty("timezone")
    @Size(max = 255)
    @Builder.Default
    private String timezone = "";

    //@NotNull
    @JsonProperty("date_of_birth")
    @Size(max = 255)
    @Builder.Default
    private String dateOfBirth = "";

}