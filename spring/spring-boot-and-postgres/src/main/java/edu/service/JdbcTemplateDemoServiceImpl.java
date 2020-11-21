package edu.service;

import edu.dto.QueryParam;
import edu.dto.QueryRequestDTO;
import edu.dto.QueryResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JdbcTemplateDemoServiceImpl implements JdbcTemplateDemoService {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateDemoServiceImpl.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateDemoServiceImpl(
        JdbcTemplate jdbcTemplate,
        NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public QueryResponseDTO select(QueryRequestDTO request) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(request.getQuery());
        return createQueryResponseDTO(sqlRowSet);
    }

    private QueryResponseDTO createQueryResponseDTO(SqlRowSet sqlRowSet) {
        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        String[] columnNames = metaData.getColumnNames();
        int columnCount = metaData.getColumnCount();

        logColumnNames(columnNames);
        logRowsValues(sqlRowSet, columnCount);

        return createQueryResponseDTO(columnNames, sqlRowSet, columnCount);
    }

    @Override
    public QueryResponseDTO selectWithParams(QueryRequestDTO request) {
        String query = request.getQuery().replace("@", ":");
        Map<String, Object> params = getParams(request);
        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(query, params);
        return createQueryResponseDTO(sqlRowSet);
    }

    private Map<String, Object> getParams(QueryRequestDTO request) {
        Map<String, Object> params = new HashMap<>();
        List<QueryParam> paramsFromRequest = request.getParams();
        for (QueryParam param : paramsFromRequest) {
            switch (param.getType()) {
                case DATE:
                    //variant 1 - doesn't work
//                    DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forID("Europe/Moscow")).parseDateTime(param.getValue());
//                    java.sql.Timestamp parsedParam = new java.sql.Timestamp(dateTime.getMillis());

                    //variant 2 - doesn't work
//                    Date parsedParam = LocalDate.parse(param.getValue()).toDateTimeAtStartOfDay(DateTimeZone.forID("Europe/Moscow")).toLocalDateTime().toDate();

                    //variant 3 - doesn't work
//                    LocalDate date = LocalDate.parse(param.getValue());
//                    java.sql.Date parsedParam = java.sql.Date.from(date.atStartOfDay(ZoneId.of("Europe/Moscow")).toInstant().atZone(ZoneId.of("UTC")).toInstant());

                    //variant 4 - doesn't work
//                    LocalDateTime localDateTime = LocalDate.parse(value).atStartOfDay(ZoneId.of("Europe/Moscow")).toLocalDateTime();
//                    Timestamp parsedParam = java.sql.Timestamp.valueOf(localDateTime);

                    //variant 5 - doesn't work
//                    Instant instant = LocalDate.parse(value).atStartOfDay(ZoneId.of("Europe/Moscow")).toInstant();
//                    Timestamp parsedParam = Timestamp.from(instant);

                    //variant 6 - work, but in that case there is no need in sql function minus_tz_offset(date_time_value timestamp, time_zone varchar)
//                    ZonedDateTime zonedDateTime = LocalDate.parse(value).atStartOfDay(ZoneId.of("Europe/Moscow"));
//                    int offsetSeconds = zonedDateTime.getOffset().getTotalSeconds();
//                    Instant instant = zonedDateTime.minusSeconds(offsetSeconds).toInstant();
//                    Timestamp parsedParam = Timestamp.from(instant);

                    LocalDate date = LocalDate.parse(param.getValue());
                    Date parsedParam = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

                    params.put(param.getName(), parsedParam);
                    break;
                case STRING:
                    params.put(param.getName(), param.getValue());
                    break;
            }
        }
        return params;
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
