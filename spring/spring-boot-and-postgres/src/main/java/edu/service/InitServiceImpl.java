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

    //todo переписать в верхнем регистре
    private void initFunctions() {
        String toTimestampWithTimeZoneFunction = "create or replace function to_timestamp_with_tz(date_time_value timestamp with time zone, time_zone varchar)\n" +
            "  returns timestamp with time zone\n" +
            "as 'select (date_time_value + extract(epoch from (date_time_value - make_timestamptz(\n" +
            "                                                                      extract(year from date_time_value) :: int,\n" +
            "                                                                      extract(month from date_time_value) :: int,\n" +
            "                                                                      extract(day from date_time_value) :: int,\n" +
            "                                                                      extract(hour from date_time_value) :: int,\n" +
            "                                                                      extract(minute from date_time_value) :: int,\n" +
            "                                                                      extract(second from date_time_value),\n" +
            "                                                                      time_zone\n" +
            "    ))) * interval ''1 second'')'\n" +
            "language sql\n" +
            "immutable\n" +
            "returns null on null input;";

        String toCharWithTimeZoneFunction = "create or replace function to_char_with_tz(date_time_value timestamp, time_format varchar, time_zone varchar)\n" +
            "  returns varchar\n" +
            "as 'select to_char(to_timestamp_with_tz(date_time_value at time zone ''UTC'', time_zone), time_format)'\n" +
            "language sql\n" +
            "immutable\n" +
            "returns null on null input;";

        String toTimestampWithUtc = "create or replace function to_utc(date_time_value timestamp, time_zone varchar)\n" +
            "  returns timestamp with time zone\n" +
            "as 'select make_timestamptz(\n" +
            "             extract(year from date_time_value :: timestamp) :: int,\n" +
            "             extract(month from date_time_value :: timestamp) :: int,\n" +
            "             extract(day from date_time_value :: timestamp) :: int,\n" +
            "             extract(hour from date_time_value :: timestamp) :: int,\n" +
            "             extract(minute from date_time_value :: timestamp) :: int,\n" +
            "             extract(second from date_time_value :: timestamp),\n" +
            "             time_zone)'\n" +
            "language sql\n" +
            "immutable\n" +
            "returns null on null input;";

        jdbcTemplate.execute(toTimestampWithTimeZoneFunction);
        jdbcTemplate.execute(toCharWithTimeZoneFunction);
        jdbcTemplate.execute(toTimestampWithUtc);
    }

    private void initDates() {
        DateTime dateTime = new DateTime(2018, 8, 23, 0, 0, 0, 0, DateTimeZone.UTC);
        for (int i = 0; i < 5; i++) {
            jodaTimeRepository.save(new JodaTime(dateTime.plusHours(i)));
        }
        jodaTimeRepository.flush();
    }
}
