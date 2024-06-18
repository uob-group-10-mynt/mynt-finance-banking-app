package com.mynt.mynt.controller;

import com.mynt.mynt.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class Auth {

  private final DemoService demoService;

  @GetMapping("")
  public String defalt(){


    // recive username && password 
    // check DB for if user exits 
    // check for correct password 

    // create function too
    // 1.create header that has algo and type 
    // 2.add infomation(claims) into data 
    // 3.hash steps 1 and 2 to create the secret key  


    return "happy day!!!";
  }

    // create function too check if JWT is valid
    // 1a.hash header and payload to create the secret key
    // 1b. if orginal secret and secret from 1a dont match == has been tampered with
    // 2. create refresh token


}
