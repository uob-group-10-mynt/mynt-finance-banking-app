
package com.mynt.banking.currency_cloud.manage.contacts.requests;

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
public class CurrencyCloudFindContactsRequest {
    
    //@NotNull
    @JsonProperty("account_name")
    @Size(max = 255)
    @Builder.Default
    private String accountName = "";
    
    //@NotNull
    @JsonProperty("account_id")
    @Size(max = 255)
    @Builder.Default
    private String accountId = "";
    
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
    @JsonProperty("page")
    @Size(max = 255)
    @Builder.Default
    private String page = "";
    
    //@NotNull
    @JsonProperty("per_page")
    @Size(max = 255)
    @Builder.Default
    private String perPage = "";
    
    //@NotNull
    @JsonProperty("order")
    @Size(max = 255)
    @Builder.Default
    private String order = "";
    
    //@NotNull
    @JsonProperty("order_asc_desc")
    @Size(max = 255)
    @Builder.Default
    private String orderAscDesc = "";
    
}