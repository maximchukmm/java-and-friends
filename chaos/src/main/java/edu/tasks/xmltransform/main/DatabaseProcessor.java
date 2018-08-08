package edu.tasks.xmltransform.main;

import java.sql.SQLException;

public interface DatabaseProcessor extends AutoCloseable {
    void connect(String url, String login, String password) throws SQLException;

    void close() throws SQLException;

    TableProcessor getTableProcessor(String tableName);
}
