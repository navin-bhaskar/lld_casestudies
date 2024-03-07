package com.splitmoney.splitmoney.controllers;

import com.splitmoney.splitmoney.models.Group;
import com.splitmoney.splitmoney.models.User;
import com.splitmoney.splitmoney.services.GroupService;
import com.splitmoney.splitmoney.services.UserService;
import dtos.*;
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

    public AddMemberResponseDto addMember(AddMemberRequestDto request) {
        String addedByAlias = request.getAddedBy();
        String groupAlias = request.getGroup();
        String memberAlias = request.getMember();

        Optional<User> addedUsr;
        Optional<Group> grp;
        Optional<User> memberUsr;
        AddMemberResponseDto resp = new AddMemberResponseDto();

        grp = groupService.findByAlias(groupAlias);
        if(grp.isEmpty()){
            resp.setStatus(ResponseStatus.FAILURE);
            resp.setMessage("Group " + groupAlias + " was not found");
            return resp;
        }

        addedUsr = userService.findByAlias(addedByAlias);
        if(addedUsr.isEmpty()){
            resp.setMessage("Created by user " + addedByAlias + " was not found");
            resp.setStatus(ResponseStatus.FAILURE);
            return resp;
        }

        memberUsr = userService.findByAlias(memberAlias);
        if(memberUsr.isEmpty()) {
            resp.setMessage("Member user " + memberAlias + " was not found");
            resp.setStatus(ResponseStatus.FAILURE);
            return resp;
        }

        groupService.addMemberToGroup(grp.get(), memberUsr.get());
        resp.setStatus(ResponseStatus.SUCCESS);
        resp.setMessage("Added user " + memberAlias + " to " + groupAlias);
        return resp;
    }
}
