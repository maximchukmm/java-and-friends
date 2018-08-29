package edu.service;

import edu.model.JodaTime;
import edu.repository.JodaTimeRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {

    private JdbcTemplate jdbcTemplate;
    private JodaTimeRepository jodaTimeRepository;

    @Autowired
    public InitServiceImpl(
        JdbcTemplate jdbcTemplate,
        JodaTimeRepository jodaTimeRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.jodaTimeRepository = jodaTimeRepository;
    }

    @Override
    public void init() {
        initDates();
        initFunctions();
    }

    private void initFunctions() {
        String minusTimeZoneOffsetFunction = "CREATE OR REPLACE FUNCTION minus_tz_offset(date_time_value TIMESTAMP, time_zone varchar)\n" +
            "  RETURNS TIMESTAMP\n" +
            "AS 'SELECT date_time_value - (date_time_value AT TIME ZONE ''UTC'' - date_time_value AT TIME ZONE time_zone)'\n" +
            "LANGUAGE SQL\n" +
            "IMMUTABLE\n" +
            "RETURNS NULL ON NULL INPUT ;";
        String plusTimeZoneOffsetFunction = "CREATE OR REPLACE FUNCTION plus_tz_offset(date_time_value TIMESTAMP, time_zone varchar)\n" +
            "  RETURNS TIMESTAMP\n" +
            "AS 'SELECT date_time_value + (date_time_value - date_time_value AT TIME ZONE time_zone)'\n" +
            "LANGUAGE SQL\n" +
            "IMMUTABLE\n" +
            "RETURNS NULL ON NULL INPUT ;";
        String toCharWithTimeZoneFunction = "CREATE OR REPLACE FUNCTION to_char_with_tz(date_time_value TIMESTAMP, time_format varchar, time_zone varchar)\n" +
            "  RETURNS varchar\n" +
            "AS 'SELECT to_char(plus_tz_offset((date_time_value AT TIME ZONE ''UTC'') :: TIMESTAMP, time_zone), time_format)'\n" +
            "LANGUAGE SQL\n" +
            "IMMUTABLE\n" +
            "RETURNS NULL ON NULL INPUT ;";

        jdbcTemplate.execute(minusTimeZoneOffsetFunction);
        jdbcTemplate.execute(plusTimeZoneOffsetFunction);
        jdbcTemplate.execute(toCharWithTimeZoneFunction);
    }

    private void initDates() {
        DateTime dateTime = new DateTime(2018, 8, 23, 0, 0, 0, 0, DateTimeZone.UTC);
        for (int i = 0; i < 48; i++) {
            jodaTimeRepository.save(new JodaTime(dateTime.plusHours(i)));
        }
        jodaTimeRepository.flush();
    }
}
