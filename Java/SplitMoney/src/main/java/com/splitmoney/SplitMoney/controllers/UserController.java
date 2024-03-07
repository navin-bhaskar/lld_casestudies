package com.splitmoney.splitmoney.controllers;

import com.splitmoney.splitmoney.models.User;
import com.splitmoney.splitmoney.services.UserService;
import dtos.AddUserRequestDto;
import dtos.AddUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    final private UserService userService;


    @Autowired
    public UserController(UserService service) {
        userService = service;
    }

    public AddUserResponseDto add(AddUserRequestDto req) {
        User usr = new User();
        AddUserResponseDto resp = new AddUserResponseDto();

        usr.setName(req.getUserName());
        usr.setPhone(req.getUserPhoneNum());
        usr.setPwd(req.getPassword());

        usr = userService.add(usr);
        resp.setUserAlias(usr.getAlias());
        return resp;
    }
}
