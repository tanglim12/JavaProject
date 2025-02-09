package repository;

import model.CrateUserRed;
import model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User getUserById(int id);
    User insertUser(CrateUserRed crateUserRed);
    User updateUserById(int id, CrateUserRed crateUserRed);
    Integer deleteUserById(int id);
}
