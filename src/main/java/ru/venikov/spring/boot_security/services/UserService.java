package ru.venikov.spring.boot_security.services;



import ru.venikov.spring.boot_security.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    User getUser(long id);

    void save(User user);

    void update(long id, User updateUser);

    void delete(long id);

    Optional<User> checkUsersName(String name);
}
