package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String INSERT_NEW = "INSERT into user (name, lastName, age) VALUES (?,?,?)";
    private static final String CREATE_TABLE = "CREATE TABLE user (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE, name VARCHAR(45), lastName VARCHAR(45), age TINYINT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS user";

    private static final String DELETE_BY_ID = "DELETE FROM user WHERE id =?";

    private Connection connection;
    private Statement statement;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("there is no connection... Exception statement!");
        }
    }

    public void createUsersTable() {
        try {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute(DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
