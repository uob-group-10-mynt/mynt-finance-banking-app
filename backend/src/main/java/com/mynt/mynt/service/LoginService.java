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
import java.util.Objects;

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
        System.out.println(loginDTO.getUsername());
        System.out.println(loginDTO.getPassword());

        // TODO: Task - 2. check DB for if user exits
        List<String> userName = this.quiryAppUser.findUsername(loginDTO.getUsername());
        boolean correctNumberOfUsernames = userName.size() == 1;
        boolean validUser = Objects.equals(userName.get(0), loginDTO.getUsername());

        // TODO: Task - 3. check for correct password
        List<String> userPassword = this.quiryAppUser.findPassword(loginDTO.getUsername());
        boolean correctAmountOfPasswords = userPassword.size() == 1;
        boolean correctPassword = Objects.equals(userPassword.get(0), loginDTO.getPassword());

        if(correctNumberOfUsernames && validUser && correctAmountOfPasswords && correctPassword){
            // TODO: James Love Task - 4. if correct password && username return JWT to the user
            jwt.setJWT(generateJwt(loginDTO.getUsername()));
        }

        return this.jwt;
    }

    public String generateJwt(String clientName) {
        JWT newToken = new JWT();
        return newToken.createJWT(clientName);
    }



}
