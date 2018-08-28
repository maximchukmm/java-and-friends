package edu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class JdbcTemplateDemoServiceImpl implements JdbcTemplateDemoService {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateDemoServiceImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateDemoServiceImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void executeAndLogSelectQuery(String selectQuery) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(selectQuery);

        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (sqlRowSet.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++)
                row.append("\t").append(sqlRowSet.getObject(i));
            log.info(row.toString());
        }
    }
}
