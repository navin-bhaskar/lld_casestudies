package com.splitmoney.splitmoney.commands;

import com.splitmoney.splitmoney.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProfile implements Command {
    private String[] words;
    final int expectedWords = 3;
    final String command = "UpdateProfile";
    @Autowired
    UserRepository userRepo;
    /**
     * This command allows user to update the profile's password
     * Format
     * user_alias UpdateProfile password
     * */
    @Override
    public boolean check(String cmdStr) {
        words = cmdStr.split(" ");
        if(words.length != expectedWords) {
            return false;
        }
        return words[1].equalsIgnoreCase(command);
    }

    @Override
    public void execute(String cmdStr) {
        words = cmdStr.split(" ");
        String userAlias = words[0];
        String password = words[2];
//        try {
//            User usr = userRepo.findByAlias(userAlias);
//            usr.setPwd(password);
//            // TODO: Save to db
//            System.out.println("Password was set for " + usr.getName());
//        } catch (UserNotFound ex) {
//            System.out.println("No user found by alias " + userAlias);
//        }
    }

    @Override
    public String help() {
        return """
                UpdateProfile: Lets user to set password
                format: user_alias UpdateProfile password
                ex: u1 UpdateProfile nanamin""";
    }
}
