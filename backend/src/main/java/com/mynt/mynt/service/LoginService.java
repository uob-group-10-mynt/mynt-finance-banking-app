package com.mynt.mynt.service;

import com.mynt.mynt.dto.JwtDto;
import com.mynt.mynt.dto.LoginDTO;
import com.mynt.mynt.model.CurrencyCloud;

import com.mynt.mynt.repository.QuiryAppUser;
import com.mynt.mynt.repository.QuiryCurrencyCloud;
import com.mynt.mynt.util.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 1. recive username && password
// 2. check DB for if user exits
// 3. check for correct password
// 4. if correct password && username return JWT to the user

@Service
public class LoginService {

    private LoginDTO loginDTO;
//    private String jwt;
    private QuiryCurrencyCloud quiryCurrencyCloud;
    private QuiryAppUser quiryAppUser;
    private JwtDto jwt;

    public LoginService(){}

    @Autowired
    public LoginService(QuiryAppUser appUser , QuiryCurrencyCloud quiryCurrencyCloud){
        this.quiryCurrencyCloud = quiryCurrencyCloud;
        this.quiryAppUser = appUser;
        this.jwt = new JwtDto();
    }

    public JwtDto getJwt(LoginDTO loginDTO) {

        // TODO: 1. recive username && password
        this.loginDTO = loginDTO;

        // TODO: Alex F Task - 2. check DB for if user exits
        System.out.println(this.quiryCurrencyCloud.findAllData());
        System.out.println(this.quiryAppUser.findAllData().get(0).getSurname());

        // TODO: Alex F Task - 3. check for correct password

        // TODO: James Love Task - 4. if correct password && username return JWT to the user
        jwt.setJWT(generateJwt(loginDTO.getUsername()));

        return this.jwt;
    }

    public String generateJwt(String clientName) {
        JWT newToken = new JWT();
        return newToken.createJWT(clientName);
    }



}
