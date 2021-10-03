package com.tistory.iqpizza6349.database;

import com.tistory.iqpizza6349.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLDatabase.class);

    static {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            String defaultPrefix = Config.PREFIX;
            statement.execute("CREATE TABLE IF NOT EXISTS guild_settings (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "guild_id VARCHAR(20) NOT NULL," +
                    "prefix VARCHAR(255) NOT NULL DEFAULT '" + defaultPrefix + "'" +
            ");");

            statement.execute("CREATE TABLE IF NOT EXISTS `music` (" +
                  "`id` INTEGER PRIMARY KEY AUTO_INCREMENT," +
                  "`guild_id` VARCHAR(20) NOT NULL," +
                  "`current_song` VARCHAR(255)," +
                  "`queue` TEXT," +
                  "`repeat` BOOLEAN NOT NULL DEFAULT FALSE ," +
                  "`count` INT(40) NOT NULL DEFAULT 0" +
            ");");
            LOGGER.info("initialized mysql connected!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;

        String url = "jdbc:mysql://localhost:3306/JDBC?serverTimezone=UTC";
        String name = Config.USER_NAME;
        String pwd = Config.USER_PASSWORD;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, name, pwd);
        return connection;
    }

}
