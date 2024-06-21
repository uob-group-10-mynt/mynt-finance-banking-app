package com.mynt.mynt.controller;

import com.mynt.mynt.dto.JwtDto;
import com.mynt.mynt.dto.LoginDTO;
import com.mynt.mynt.service.*;
//import com.mynt.banking.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

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
    if(token == null){
      return ResponseEntity.badRequest().build();
    }
    // else return token
    return ResponseEntity.ok(token);
  }


}
