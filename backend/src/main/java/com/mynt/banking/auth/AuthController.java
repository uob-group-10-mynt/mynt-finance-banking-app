package com.mynt.banking.auth;

import com.mynt.banking.service.LoginService;
import com.mynt.mynt.service.*;
//import com.mynt.banking.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final LoginService loginService;

  @Autowired
  public AuthController(LoginService loginService){
    this.loginService = loginService;
  }


  @PostMapping("")
  public ResponseEntity<Object> defalt(@RequestBody LoginDTO loginDTO){
    //return 400 if no data is imputed
    if(loginDTO.getUsername() == null || loginDTO.getPassword() == null){
      return (ResponseEntity<Object>) ResponseEntity.badRequest().build();
    }

    //check for token == null
    JwtDto token = this.loginService.getJwt(loginDTO);
    String errorMessage = "Error - Invalid username or password";
    if(Objects.equals(token.getJWT(), errorMessage)){
      return ResponseEntity.badRequest().body(token);
    }
    // else return token
    return ResponseEntity.ok(token);
  }


}
