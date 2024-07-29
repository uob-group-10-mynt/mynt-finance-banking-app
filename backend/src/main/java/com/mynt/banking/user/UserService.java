package com.mynt.banking.user;

import com.mynt.banking.auth.JwtUserDetails;
import com.mynt.banking.auth.TokenService;
import com.mynt.banking.user.requests.ChangePasswordRequest;
import com.mynt.banking.user.requests.UpdateUserDetailsRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.*;
import com.mynt.banking.user.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final ContactsService contactsService;


  public void changePassword(@NotNull ChangePasswordRequest request, Principal connectedUser) {
    var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new IllegalStateException("Wrong password");
    }
    if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
      throw new IllegalStateException("Password are not the same");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public GetUserDetailsResponse getUserDetails(@NotNull String auth) throws IOException {
    String accessToken = auth.substring(7);
    String userEmail = tokenService.extractUsername(accessToken);

    var user = userRepository.findByEmail(userEmail).orElseThrow();
    return GetUserDetailsResponse.builder()
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .dob(user.getDob())
            .phoneNumber(user.getPhone_number())
            .address(user.getAddress())
            .build();
  }

  public void updateUserDetails(@NotNull UpdateUserDetailsRequest request) throws RuntimeException {

    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();

    String userEmail = principal.getUsername();
    User user = userRepository.findByEmail(userEmail).orElseThrow();
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    user.setDob(request.getDob().toString());
    user.setPhone_number(request.getPhoneNumber());
    user.setAddress(request.getAddress());
    userRepository.save(user);

    String currencyCloudContactUUID = principal.getUuid();

    UpdateContactRequest updateContactRequest = UpdateContactRequest.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .dateOfBirth(request.getDob().toString())
            .phoneNumber(request.getPhoneNumber())
            .build();

    ResponseEntity<JsonNode> updateContactResponse = contactsService
            .updateContact(currencyCloudContactUUID, updateContactRequest)
            .block();

      assert updateContactResponse != null;
      if (updateContactResponse.getStatusCode().isError()) throw new RuntimeException("");
  }
}