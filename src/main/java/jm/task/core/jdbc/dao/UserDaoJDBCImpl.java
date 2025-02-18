package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public void createUsersTable() {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                    name VARCHAR(255),
                    lastName VARCHAR(255),
                    age TINYINT
                )
                """;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица 'users' успешно создана.");
    }

    public void dropUsersTable() {
        String del = "DROP TABLE IF EXISTS user";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(del);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица 'users' успешно удалена.");
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserQuery = """
                INSERT INTO user(name, lastName, age) VALUES (?, ?, ?);""";

        if (name == null || name.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Имя и фамилия не должны быть пустыми.");
        }

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Пользователь %s %s был успешно добавлен в базу данных.\n", name, lastName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Запись успешно удалена");
            } else {
                System.out.println("Не найдено записей для удаления");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении записи: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String selectAllUsersQuery = "SELECT * FROM user;";
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastName, age);
                allUsers.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (User user : allUsers) {
            System.out.println(user);
        }
        return allUsers;
    }


    public void cleanUsersTable() {
        String selectAllUsersQuery = "TRUNCATE TABLE user";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(selectAllUsersQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица 'users' успешно очищена.");
    }
}

