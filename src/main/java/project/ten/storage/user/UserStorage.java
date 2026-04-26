package project.ten.storage.user;

import project.ten.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(long id);
    Collection<User> getUsers();
}
