package edu.tasks.xmltransform.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteFirstConnection {
    static void insertNumbers(Connection connection, int countOfNumbers) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("insert into TEST (FIELD) values");
        for (int i = 0; i < countOfNumbers - 1; i++) {
            insertQuery.append(" (" + (int) (20 * Math.random()) + "),");
        }
        insertQuery.append(" (" + (int) (20 * Math.random()) + ")");
        statement.executeUpdate(insertQuery.toString());
        statement.close();
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./res/magnit.db");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet checkIsTableTESTEmpty = statement.executeQuery("select 'NOT_EMPTY' where exists (select FIELD from TEST limit 1)");
            if (checkIsTableTESTEmpty.next()) {
                if (checkIsTableTESTEmpty.getString(1).equals("NOT_EMPTY")) {
                    statement.executeUpdate("delete from TEST");
                }
            }
            insertNumbers(connection, 5);
            ResultSet resultSet = statement.executeQuery("select * from TEST");
            while (resultSet.next()) {
                System.out.println("FIELD: " + resultSet.getInt("FIELD"));
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
