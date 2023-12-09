package services.CommandHandler.commands;

import models.User;
import repositories.UserRepository;
import services.exceptions.UserAlreadyInRepo;

public class Register implements Command{

    private String[] words;
    final int expectedWords = 4;
    final String command = "register";
    final UserRepository userRepo = new UserRepository();
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

        User usr = new User();
        usr.setName(userName);
        usr.setPhone(phone);
        usr.setPwd(password);

        try {
            usr = userRepo.addUser(usr);
            System.out.println("Added user " + usr.getName() + " with alias " + usr.getAlias());
        } catch (UserAlreadyInRepo ex) {
            System.out.println("User " + userName + " already in the repo");
        }
    }

    @Override
    public String help() {
        return """
                Register: register a user
                Format: Register user_name phone_number pass_word
                ex: Register gojosatoru 007 thegoat""";
    }
}
