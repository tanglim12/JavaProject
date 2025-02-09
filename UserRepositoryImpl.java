package repository;

import model.CrateUserRed;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserRepositoryImpl implements UserRepository {
    private final String url = "jdbc:postgresql://localhost:5432/media_db";
    private final String userName = "postgres";
    private final String password = "1111";


    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("""

                    SELECT *FROM users WHERE is_deleted = false""");
            while (resultSet.next() ){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDeleted(resultSet.getBoolean("is_deleted"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            System.out.printf(e.getMessage());
        }
        return List.of();
    }

    @Override
    public User getUserById(int id) {
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            PreparedStatement preparedStatement = connection.prepareStatement("""

                    SELECT *FFROM users where id = ?""");
            preparedStatement.setInt(1 ,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            while (resultSet.next() ){
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDeleted(resultSet.getBoolean("is_deleted"));
            }
            return user;
        }catch (Exception exception){
            System.out.println("Error during get user by ID:" + exception.getMessage());
        }


        return null;
        }


    @Override
    public User insertUser(CrateUserRed crateUserRed) {
        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
INSERT INTO user(id,user,email,password,is_deleted)
VALUES (?,?,?,?,?)"""
            );
            int userId = new Random().nextInt(9999);
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,crateUserRed.username());
            preparedStatement.setString(3,crateUserRed.password());
            preparedStatement.setString(4,crateUserRed.password());
            preparedStatement.setBoolean(5,false);
            int rowAffectted = preparedStatement.executeUpdate();
            String message = rowAffectted>0? "User has been created":" Cannot create user";
                System.out.println(message);
                if (rowAffectted > 0){
                    return new User(userId,crateUserRed.username(), crateUserRed.email(),crateUserRed.password(),false);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User updateUserById(int id, CrateUserRed crateUserRed) {

        return null;
    }

    @Override
    public Integer deleteUserById(int id) {
        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            PreparedStatement preparedStatement = connection.prepareStatement("""
    DELETE  FROM user WHERE id = ?
""");
            preparedStatement.setInt(1,id);
            int rowDeleted = preparedStatement.executeUpdate();
            String message = rowDeleted>0?"User with ID: "+id+" has been deleted":"user not found";
            System.out.printf(message);
            return rowDeleted;
        }catch (Exception exception){
            System.out.printf("Error during deleted user by id "+ exception.getMessage());
        }
        return 0;
    }
}
