package edu.service;

import edu.dto.QueryRequestDTO;
import edu.dto.QueryResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JdbcTemplateDemoServiceImpl implements JdbcTemplateDemoService {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateDemoServiceImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateDemoServiceImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public QueryResponseDTO select(QueryRequestDTO request) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(request.getQuery());
        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        String[] columnNames = metaData.getColumnNames();
        int columnCount = metaData.getColumnCount();

        logColumnNames(columnNames);
        logRowsValues(sqlRowSet, columnCount);

        return createQueryResponseDTO(columnNames, sqlRowSet, columnCount);
    }

    private QueryResponseDTO createQueryResponseDTO(String[] columnNames, SqlRowSet sqlRowSet, int columnCount) {
        QueryResponseDTO queryResponseDTO = new QueryResponseDTO();
        queryResponseDTO.setTitles(Arrays.asList(columnNames));
        while (sqlRowSet.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(sqlRowSet.getObject(i).toString());
            }
            queryResponseDTO.getRows().add(row);
        }
        return queryResponseDTO;
    }

    private void logRowsValues(SqlRowSet sqlRowSet, int columnCount) {
        while (sqlRowSet.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++)
                row.append("\t\t\t").append(sqlRowSet.getObject(i));
            log.info(row.toString());
        }
        sqlRowSet.beforeFirst();
    }

    private void logColumnNames(String[] columnNames) {
        StringBuilder columns = new StringBuilder();
        for (int i = 0; i < columnNames.length; i++) {
            columns.append("\t\t\t").append(columnNames[i]);
        }
        log.info(columns.toString());
    }
}
