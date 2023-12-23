package com.splitmoney.splitmoney.services;

import com.splitmoney.splitmoney.models.User;
import com.splitmoney.splitmoney.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Setter
@Getter
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User usr) {
        return userRepository.save(usr);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByAlias(String alias) {
        Optional<Long> id = User.getIdFromAlias(alias);
        if (id.isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findById(id.get());
    }

    public static String getUsrNotFoundMsg(String usr) {
        return "User with alias " + usr + " was not found ";
    }
}
