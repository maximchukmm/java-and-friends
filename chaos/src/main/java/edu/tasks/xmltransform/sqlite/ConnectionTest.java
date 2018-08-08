package edu.tasks.xmltransform.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./res/test.db");
            Statement countRows = connection.createStatement();
            ResultSet numberOfRows = countRows.executeQuery("select count(*) from TEST;");
            int n = 0;
            if (numberOfRows.next()) {
                n = numberOfRows.getInt(1);
            }
            numberOfRows.close();
            countRows.close();
            if (n != 0) {
                int index = 0;
                int step = 10;
                PreparedStatement getValues = connection.prepareStatement("select FIELD from TEST limit ?, ?;");
                ResultSet values = null;
                while (index < n) {
                    getValues.setInt(1, index);
                    getValues.setInt(2, step);
                    index += step;
                    values = getValues.executeQuery();

                    while (values.next()) {
                        System.out.print("\t" + values.getInt(1));
                    }
                    System.out.println();
                }
                if (values != null) {
                    values.close();
                }
                getValues.close();
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
