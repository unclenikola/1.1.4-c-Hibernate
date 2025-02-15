package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "root";



    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("соединение установленно");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("соединение ERROR");
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                Map<String, String> settings = new HashMap<>();
                settings.put("hibernate.connection.driver_class", DRIVER);
                settings.put("hibernate.connection.url", URL);
                settings.put("hibernate.connection.username", USER);
                settings.put("hibernate.connection.password", PASSWORD);
                settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                settings.put("hibernate.show_sql", "true"); // Show SQL in console
                settings.put("hibernate.format_sql", "true"); // Format SQL for readability
                settings.put("hibernate.hbm2ddl.auto", "update"); // Automatically update schema

                registryBuilder.applySettings(settings);

                StandardServiceRegistry registry = registryBuilder.build();

                MetadataSources metadataSources = new MetadataSources(registry);
                metadataSources.addAnnotatedClass(User.class); // Add your entity classes

                Metadata metadata = metadataSources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();
                System.out.println("SessionFactory created successfully.");

            } catch (Exception e) {
                System.err.println("SessionFactory creation failed: " + e.getMessage());
                e.printStackTrace();
                throw new ExceptionInInitializerError("Failed to create session factory: " + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("SessionFactory closed.");
        }
    }
}