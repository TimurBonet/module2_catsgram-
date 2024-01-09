package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    private final HashMap<String, User> users = new HashMap<>();

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        for (User u : users.values()){
            userList.add(u);
        }
        return userList;
    }

    public User createUser (@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
        if(!user.getEmail().isEmpty() && !user.getEmail().isBlank()){
            if (!users.containsKey(user.getEmail())){
                users.put(user.getEmail(),user);
            } else {
                throw new UserAlreadyExistException();
            }
        } else {
            throw new InvalidEmailException();
        }
        return user;
    }

    public User setUser (User user) throws InvalidEmailException {
        if(!user.getEmail().isEmpty() || !user.getEmail().isBlank()){
            if (users.containsKey(user.getEmail())){
                users.put(user.getEmail(),user);
            } else {
                users.put(user.getEmail(),user);
            }
        } else {
            throw new InvalidEmailException();
        }
        return user;
    }

    public User findUserByEmail(String email) {
            if (email == null){
                return null;
            }
            return users.get(email);
    }
}
