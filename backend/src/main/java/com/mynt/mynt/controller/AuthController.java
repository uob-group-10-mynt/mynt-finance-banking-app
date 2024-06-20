package com.mynt.mynt.controller;

import com.mynt.mynt.dto.LoginDTO;
import com.mynt.mynt.service.*;
//import com.mynt.banking.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final DemoService demoService;

  @PostMapping("")
  public ResponseEntity<Object> defalt(@RequestBody LoginDTO loginDTO){
    //return 400 if no data is imputed
    if(loginDTO.getUsername() == null || loginDTO.getPassword() == null){
      return (ResponseEntity<Object>) ResponseEntity.badRequest().build();
    }
    LoginService loginService = new LoginService(loginDTO);
    return ResponseEntity.ok(loginService.getJwt());
  }


}
