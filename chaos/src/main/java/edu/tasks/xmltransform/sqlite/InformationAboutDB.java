package edu.tasks.xmltransform.sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InformationAboutDB {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./res/test.db");
            final DatabaseMetaData metaData = connection.getMetaData();
            System.out.println("getDatabaseProductName: " + metaData.getDatabaseProductName());
            System.out.println("getDatabaseProductVersion: " + metaData.getDatabaseProductVersion());
            System.out.println("getDriverName: " + metaData.getDriverName());
            System.out.println("getDriverVersion: " + metaData.getDriverVersion());
            System.out.println("getUserName: " + metaData.getUserName());

            Statement info = connection.createStatement();
            final ResultSet resultSet = info.executeQuery("select sql from sqlite_master where name='TEST';");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqlex) {
                    System.out.println("Cannot close connection.");
                }
            }
        }
    }
}
