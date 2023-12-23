package com.splitmoney.splitmoney.commands;

import com.splitmoney.splitmoney.controllers.UserController;
import dtos.AddUserRequestDto;
import dtos.AddUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Register implements Command{

    private String[] words;
    final private int expectedWords = 4;
    private final String command = "register";
    private final UserController addUserController;

    @Autowired
    public Register(UserController controller) {
        this.addUserController = controller;
    }

    /**
     * The register command allows user to register with the system
     * Format of the input
     *  Register user_name phone password
     *[]-> 0          1       2     3
     * */
    @Override
    public boolean check(String cmdStr) {
        words = cmdStr.split(" ");
        if(words.length != expectedWords) {
            return false;
        }
        return words[0].equalsIgnoreCase(command);
    }

    @Override
    public void execute(String cmdStr) {
        words = cmdStr.split(" ");
        String userName = words[1];
        String phone = words[2];
        String password = words[3];

        AddUserRequestDto request = new AddUserRequestDto();
        request.setUserName(userName);
        request.setPassword(password);
        request.setUserPhoneNum(phone);

        AddUserResponseDto response = addUserController.add(request);

        System.out.println("Added user " + userName + " with alias " + response.getUserAlias());
    }

    @Override
    public String help() {
        return """
                Register: register a user
                Format: Register user_name phone_number pass_word
                ex: Register gojosatoru 007 thegoat""";
    }
}
