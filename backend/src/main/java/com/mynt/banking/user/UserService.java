//package com.mynt.banking.user;
//
//
//import com.mynt.banking.user.request.ChangePasswordRequest;
//import com.mynt.banking.user.request.UserSignupRequest;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import java.security.Principal;
//
//@Service
//public interface UserService {
//
//  public String signup(UserSignupRequest request);
//
//  UserDetailsService userDetailsService();
//
////  PasswordEncoder passwordEncoder;
////
////  private final UserRepository repository;
//
//  public void changePassword(ChangePasswordRequest request, Principal connectedUser);
//}

package com.mynt.banking.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.auth.JWTService;
import com.mynt.banking.currency_cloud.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.CurrencyCloudRepository;
import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.*;
import com.mynt.banking.user.requests.*;
import com.mynt.banking.user.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JWTService jwtService;
  private final CurrencyCloudRepository currencyCloudRepository;
  private final ContactsService contactsService;

  public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
    var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new IllegalStateException("Wrong password");
    }
    if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
      throw new IllegalStateException("Password are not the same");
    }

    // update the password
    user.setPassword(passwordEncoder.encode(request.getNewPassword()));

    // save the new password
    userRepository.save(user);
  }

  public GetUserDetailsResponse getUserDetails(String auth) throws IOException {
    String accessToken = auth.substring(7);
    String userEmail = jwtService.extractUsername(accessToken);

    var user = userRepository.findByEmail(userEmail).orElseThrow();
    return GetUserDetailsResponse.builder()
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .dob(user.getDob())
            .phoneNumber(user.getPhone_number())
            .address(user.getAddress())
            .build();
  }

  public void updateUserDetails(String auth, UpdateUserDetailsRequest request) throws RuntimeException {
    String accessToken = auth.substring(7);
    String userEmail = jwtService.extractUsername(accessToken);

    User user = userRepository.findByEmail(userEmail).orElseThrow();
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    user.setDob(request.getDob().toString());
    user.setPhone_number(request.getPhoneNumber());
    user.setAddress(request.getAddress());
    userRepository.save(user);

    List<CurrencyCloudEntity> currencyCloudUserList = currencyCloudRepository.findByUsersId(user.getId());
    if(currencyCloudUserList.isEmpty()) throw new NoSuchElementException("No currency cloud account found");
    CurrencyCloudEntity currencyCloudUser = currencyCloudUserList.get(0);
    String currencyCloudContactUUID = currencyCloudUser.getUuid();

    UpdateContactRequest updateContactRequest = UpdateContactRequest.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .dateOfBirth(request.getDob().toString())
            .phoneNumber(request.getPhoneNumber())
            .build();

    ResponseEntity<JsonNode> updateContactResponse = contactsService
            .updateContact(currencyCloudContactUUID, updateContactRequest)
            .block();

    if (updateContactResponse.getStatusCode().isError()) throw new RuntimeException("");
  }
}