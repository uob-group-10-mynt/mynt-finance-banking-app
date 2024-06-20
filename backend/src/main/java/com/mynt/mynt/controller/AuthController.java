package com.mynt.mynt.controller;

import com.mynt.mynt.dto.LoginDTO;
import com.mynt.mynt.service.*;
//import com.mynt.banking.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final DemoService demoService;

  @PostMapping("")
  public String defalt(@RequestBody LoginDTO loginDTO){

    LoginService loginService = new LoginService(loginDTO);

    // add methord to retrive data for responce body


    return "happy day!!!";
  }

    // create function too check if JWT is valid
    // 1a.hash header and payload to create the secret key
    // 1b. if orginal secret and secret from 1a dont match == has been tampered with
    // 2. create refresh token


}
