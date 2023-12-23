package com.splitmoney.splitmoney.controllers;

import com.splitmoney.splitmoney.models.Group;
import com.splitmoney.splitmoney.models.User;
import com.splitmoney.splitmoney.services.GroupService;
import com.splitmoney.splitmoney.services.UserService;
import dtos.AddGroupRequestDto;
import dtos.AddGroupResponseDto;
import dtos.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class GroupController {

    final private GroupService groupService;
    final private UserService userService;

    private void setUserNotFoundResp(AddGroupResponseDto resp, String userAlias) {
        resp.setError("Admin user " + userAlias + " was not found ");
        resp.setStatus(ResponseStatus.FAILURE);

    }

    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }


    public AddGroupResponseDto add(AddGroupRequestDto request) {
        String userAlias = request.getAdmin();
        String groupName = request.getGroupName();
        AddGroupResponseDto resp = new AddGroupResponseDto();
        Group grp = new Group();

        Optional<Long> userId = User.getIdFromAlias(userAlias);
        if(userId.isEmpty()) {
            setUserNotFoundResp(resp, userAlias);
        } else {
            Optional<User> usr = userService.findById(userId.get());
            if (usr.isEmpty()) {
                setUserNotFoundResp(resp, userAlias);
            } else {
                grp.setCreatedBy(usr.get());
                grp.setName(groupName);
                grp = groupService.add(grp);
                resp.setAlias(grp.getGroupAlias());
                resp.setStatus(ResponseStatus.SUCCESS);
            }
        }
        return resp;
    }
}
