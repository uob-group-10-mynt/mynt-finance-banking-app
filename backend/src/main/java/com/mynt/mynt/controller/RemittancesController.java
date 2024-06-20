package com.mynt.mynt.controller;

import com.mynt.mynt.dto.LoginDTO;
import com.mynt.mynt.service.DemoService;
import com.mynt.mynt.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.mynt.dto.LoginDTO;
import com.mynt.mynt.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/remittance")
public class RemittancesController {

    private final DemoService demoService;

//    @PostMapping("")
//    public ResponseEntity<Object> reciveMoney(@RequestBody LoginDTO loginDTO){
//        //return 400 if no data is imputed
//        if(loginDTO.getUsername() == null || loginDTO.getPassword() == null){
//            return (ResponseEntity<Object>) ResponseEntity.badRequest().build();
//        }
//        LoginService loginService = new LoginService(loginDTO);
//        return ResponseEntity.ok(loginService.getJwt());
//    }
//
//    public ResponseEntity<Object> sendMoney(@RequestBody LoginDTO loginDTO){
//        //return 400 if no data is imputed
//        if(loginDTO.getUsername() == null || loginDTO.getPassword() == null){
//            return (ResponseEntity<Object>) ResponseEntity.badRequest().build();
//        }
//        LoginService loginService = new LoginService(loginDTO);
//        return ResponseEntity.ok(loginService.getJwt());
//    }


}











