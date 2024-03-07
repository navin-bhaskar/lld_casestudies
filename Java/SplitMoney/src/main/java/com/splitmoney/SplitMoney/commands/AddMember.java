package com.splitmoney.splitmoney.commands;

import com.splitmoney.splitmoney.controllers.GroupController;
import dtos.AddMemberRequestDto;
import dtos.AddMemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddMember implements Command{
    @Autowired
    private GroupController groupController;
    final private String command = "addmember";
    private String[] words;

    @Override
    public boolean check(String cmdStr) {
        words = cmdStr.split(" ");
        return words.length == 4 && words[1].equalsIgnoreCase(command);
    }

    @Override
    public void execute(String cmdStr) {
        words = cmdStr.split(" ");
        String addedBy = words[0];
        String group = words[2];
        String member = words[3];

        AddMemberRequestDto request = new AddMemberRequestDto();
        request.setMember(member);
        request.setGroup(group);
        request.setAddedBy(addedBy);

        AddMemberResponseDto resp = groupController.addMember(request);
        System.out.println(resp.getMessage());
    }

    @Override
    public String help() {
        return """
                Adds an user to group
                Format: user AddMember group user
                Ex: u1 AddMember g1 u2
                U1 is adding u2 to g1""";
    }
}
