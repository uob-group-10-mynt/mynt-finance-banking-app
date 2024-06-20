package com.mynt.mynt.service;

import com.mynt.mynt.dto.LoginDTO;

// 1. recive username && password
// 2. check DB for if user exits
// 3. check for correct password
// 4. if correct password && username return JWT to the user

public class LoginService {

    private LoginDTO loginDTO;

    public LoginService(){}

    public LoginService(LoginDTO loginDTO){

        // TODO: 1. recive username && password
        this.loginDTO = loginDTO;

        // TODO: Alex F Task - 2. check DB for if user exits

        // TODO: Alex F Task - 3. check for correct password

        // TODO: James Love Task - 4. if correct password && username return JWT to the user

    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }




}
