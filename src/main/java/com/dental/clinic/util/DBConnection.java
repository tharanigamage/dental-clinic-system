package com.dental.clinic.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;

    private Connection connection;

    private String url;
    private String username;
    private String password;

    private DBConnection(){
        loadProperties();
        connect();
    }

    public static DBConnection getInstance(){
        if (instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    private void loadProperties(){
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")){
            if (input == null){
                throw new RuntimeException("db.properties file not found in resources folder");
            }
            props.load(input);
            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
        }
        catch (IOException e){
            throw new RuntimeException("Failed to load db.properties", e);
        }
    }

    private void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully.");
        }
        catch (ClassNotFoundException e){
            throw new RuntimeException("MYSQL JDBC Driver not found", e);
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public Connection getConnection(){
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Error checking database connection", e);
        }
        return connection;
    }
}
