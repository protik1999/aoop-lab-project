package com.teamvoid.gocircle;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnect()
    {
        String databaseName= Dotenv.load().get("DB_NAME");
        String databaseUsername=Dotenv.load().get("DB_USERNAME");
        String databasePassword=Dotenv.load().get("DB_PASSWORD");
        String url = Dotenv.load().get("DB_HOST")+"/"+databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUsername,databasePassword);
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        return databaseLink;
    }

}
