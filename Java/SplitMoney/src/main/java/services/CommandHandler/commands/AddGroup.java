package services.CommandHandler.commands;

import models.Group;
import models.User;
import repositories.GroupRepository;
import repositories.UserRepository;
import services.exceptions.GroupAlreadyInRepo;
import services.exceptions.UserNotFound;

public class AddGroup implements Command{
    private String[] words;
    final int expectedWords = 3;
    final String command = "AddGroup";
    final UserRepository userRepo = new UserRepository();
    final GroupRepository grpRepo = new GroupRepository();
    /**
     * The AddGroup command allows user create a group
     * Format of the input
     *  user_alias AddGroup groupName
     *[]-> 0          1       2
     * */
    @Override
    public boolean check(String cmdStr) {
        words = cmdStr.split(" ");
        if(words.length != 3) {
            return false;
        }
        return words[1].equalsIgnoreCase(command);
    }

    @Override
    public void execute(String cmdStr) {
        words = cmdStr.split(" ");
        String usrAlias = words[0];
        String grpName = words[2];
        User usr;
        try {
            usr = userRepo.findUserByAlias(usrAlias);
        } catch (UserNotFound ex) {
            System.out.println("No user found for alias " + usrAlias);
            return;
        }

        Group grp = new Group();
        grp.setName(grpName);
        grp.setAdmin(usr);
        try {
            grp = grpRepo.addGroup(grp);
            System.out.println("Group with alias " + grp.getAlias() + " added to repo by " + usr.getName());
        } catch (GroupAlreadyInRepo ex) {
            System.out.println("Group with name " + grp + " already exists ");
        }
    }

    @Override
    public String help(){
        return """
                AddGroup: Adds a group to the repo
                format: user_alias AddGroup group_name
                ex: u1 AddGroup sectionA""";
    }
}
