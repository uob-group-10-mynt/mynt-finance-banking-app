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

import com.mynt.banking.auth.JWTService;
import com.mynt.banking.user.requests.ChangePasswordRequest;
import com.mynt.banking.user.requests.UpdateUserDetailsRequest;
import com.mynt.banking.user.responses.GetUserDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JWTService jwtService;

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

  public void updateUserDetails(String auth, UpdateUserDetailsRequest request) throws NoSuchElementException {
    String accessToken = auth.substring(7);
    String userEmail = jwtService.extractUsername(accessToken);

    var user = userRepository.findByEmail(userEmail).orElseThrow();
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    user.setDob(request.getDob().toString());
    user.setPhone_number(request.getPhoneNumber());
    user.setAddress(request.getAddress());
    userRepository.save(user);

  }
}