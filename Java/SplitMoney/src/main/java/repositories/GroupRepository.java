package repositories;

import models.Group;
import models.User;
import services.exceptions.GroupAlreadyInRepo;

import java.util.HashMap;
import java.util.Map;

public class GroupRepository {
    private static Map<String, Group> groupNameMap = new HashMap<>();
    private static int groupAliasCnt = 1;


    public Group addGroup(Group grp) throws GroupAlreadyInRepo {
        if (groupNameMap.containsKey(grp.getName())) {
            throw new GroupAlreadyInRepo();
        }
        String grpAlias = "u" + groupAliasCnt;
        groupNameMap.put(grpAlias, grp);
        grp.setAlias(grpAlias);
        groupAliasCnt += 1;
        // TODO: No ID is being added will be fixed when we move to the repo
        return grp;
    }
}
