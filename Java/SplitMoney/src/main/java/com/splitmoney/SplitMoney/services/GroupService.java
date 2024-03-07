package com.splitmoney.splitmoney.services;

import com.splitmoney.splitmoney.models.Group;
import com.splitmoney.splitmoney.models.User;
import com.splitmoney.splitmoney.repositories.GroupRepository;
import com.splitmoney.splitmoney.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group add(Group grp) {
        return groupRepository.save(grp);
    }
    public Optional<Group> findByAlias(String alias) {
        Optional<Long> id = Group.getIdFromAlias(alias);
        if (id.isEmpty()) {
            return Optional.empty();
        }
        return groupRepository.findById(id.get());
    }
    public void addMemberToGroup(Group group, User user) {
        group.addUser(user);
        groupRepository.save(group);

    }

    public static String getGrpNotFoundMsg(String grp) {
        return "Group with alias " + grp + " was not found ";
    }
}
