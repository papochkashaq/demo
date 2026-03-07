package com.alderson.demo.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class Postgres {
    //where is pojo for entity layer?

    private static final Properties PROPERTIES = new Properties();
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error to get connection with DB " + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    private static void loadProperties() {
        try (InputStream in = Postgres.class.getClassLoader().getResourceAsStream("db.properties")) {
            PROPERTIES.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL = PROPERTIES.getProperty("db.url");
        USERNAME = PROPERTIES.getProperty("db.username");
        PASSWORD = PROPERTIES.getProperty("db.password");
    }

    private static void initDB() {
        // single responsibility problem, class inits db and accesses it, separate data access part and db initiation
        Scanner scanner = new Scanner(Postgres.class.getClassLoader().getResourceAsStream("schema.sql"));
        try {
            Statement stmt = getConnection().createStatement();
            while (scanner.hasNextLine()) {
                stmt.execute(scanner.nextLine());
            }
        } catch (SQLException e) {
            System.out.println("Error to init DB " + Arrays.toString(e.getStackTrace()));
        }
    }

    public static void main() {
        loadProperties();
        initDB();
    }

}
