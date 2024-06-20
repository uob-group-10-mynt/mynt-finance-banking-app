package com.mynt.mynt.service;

import com.mynt.mynt.dto.LoginDTO;
import com.mynt.mynt.model.CurrencyCloud;
import com.mynt.mynt.repository.FindPassword;
import com.mynt.mynt.repository.UUID;
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
    private String jwt;
    private UUID uuid;

    public LoginService(){}

    @Autowired
    public LoginService(UUID uuid){
        this.uuid = uuid;
    }

    public String getJwt(LoginDTO loginDTO) {

        // TODO: 1. recive username && password
        this.loginDTO = loginDTO;

        // TODO: Alex F Task - 2. check DB for if user exits

        // TODO: Alex F Task - 3. check for correct password

        // TODO: James Love Task - 4. if correct password && username return JWT to the user
        this.jwt = generateJwt(loginDTO.getUsername());

        return this.jwt;
    }

    public String generateJwt(String clientName) {
        JWT newToken = new JWT();
        return newToken.createJWT(clientName);
    }



}
