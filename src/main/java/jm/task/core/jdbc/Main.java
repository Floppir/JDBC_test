package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/newbdfortask";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alex", "Petrov", (byte) 19);
        System.out.println("User с именем Alex добавлен в базу данных");
        userService.saveUser("Dimon", "Sergeev", (byte) 25);
        System.out.println("User с именем Dimon добавлен в базу данных");
        userService.saveUser("Bob", "Ivanov", (byte) 30);
        System.out.println("User с именем Bob добавлен в базу данных");
        userService.saveUser("Vova", "Malenkov", (byte) 23);
        System.out.println("User с именем Vova добавлен в базу данных");

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
