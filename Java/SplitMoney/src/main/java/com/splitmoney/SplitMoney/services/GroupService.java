package com.splitmoney.splitmoney.services;

import com.splitmoney.splitmoney.models.Group;
import com.splitmoney.splitmoney.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group add(Group grp) {
        return groupRepository.save(grp);
    }
}
