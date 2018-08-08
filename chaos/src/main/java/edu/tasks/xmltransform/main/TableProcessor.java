package edu.tasks.xmltransform.main;

import java.sql.SQLException;
import java.util.Collection;

public interface TableProcessor {
    boolean isTableExists(String table);

    void createTable(String createTableStatement) throws SQLException;

    <E extends Collection> void insertValues(Collection<E> values) throws SQLException;

    <E> Collection<E> getColumn(int columnIndex) throws SQLException;
}
