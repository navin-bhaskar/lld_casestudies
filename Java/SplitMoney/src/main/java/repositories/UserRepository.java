package repositories;

import lombok.Getter;
import lombok.Setter;
import models.User;
import services.exceptions.UserAlreadyInRepo;
import services.exceptions.UserNotFound;

import java.util.HashMap;
import java.util.Map;

// TODO: Move to real repo later
@Getter
@Setter
public class UserRepository {
    private static Map<String, User> userAliasMap = new HashMap<>();
    private static Map<String, User> userNameMap = new HashMap<>();
    private static int userAliasCnt = 1;


    public User addUser(User usr) throws UserAlreadyInRepo {
        String userName = usr.getName();
        if (userNameMap.containsKey(usr.getName())) {
            throw new UserAlreadyInRepo();
        }
        String userAlias = "u" + userAliasCnt;
        userNameMap.put(userName, usr);
        userAliasMap.put(userAlias, usr);
        usr.setAlias(userAlias);
        userAliasCnt += 1;
        // TODO: No ID is being added will be fixed when we move to the repo
        return usr;
    }

    public User findUserByAlias(String alias) throws UserNotFound{
        if(userAliasMap.containsKey(alias)) {
            return userAliasMap.get(alias);
        }
        throw new UserNotFound();
    }
}
