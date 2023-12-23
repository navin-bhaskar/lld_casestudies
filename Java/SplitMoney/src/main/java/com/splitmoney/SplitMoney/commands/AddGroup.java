package com.splitmoney.splitmoney.commands;


import com.splitmoney.splitmoney.controllers.GroupController;
import dtos.AddGroupRequestDto;
import dtos.AddGroupResponseDto;
import dtos.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddGroup implements Command{
    private String[] words;
    final int expectedWords = 3;
    final String command = "AddGroup";

    @Autowired
    private GroupController groupController;
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
    public void execute(String cmdStr){
        words = cmdStr.split(" ");
        String usrAlias = words[0];
        String grpName = words[2];
        AddGroupRequestDto req = new AddGroupRequestDto();
        req.setAdmin(usrAlias);
        req.setGroupName(grpName);

        AddGroupResponseDto resp = groupController.add(req);
        if (resp.getStatus() == ResponseStatus.SUCCESS) {
            System.out.println("Added group with group name " + resp.getAlias());
        } else {
            System.out.println(resp.getError());
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
