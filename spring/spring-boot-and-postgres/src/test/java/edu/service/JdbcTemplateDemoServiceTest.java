package edu.service;

import edu.dto.QueryParam;
import edu.dto.QueryRequestDTO;
import edu.dto.QueryResponseDTO;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class JdbcTemplateDemoServiceTest {
    @Autowired
    private JdbcTemplateDemoService jdbcTemplateDemoService;

    private QueryResponseDTO getResultOfSelect(String selectQuery) {
        QueryRequestDTO request = new QueryRequestDTO(selectQuery);
        return jdbcTemplateDemoService.select(request);
    }

    @Test
    public void select_WhenSelectFirstValueOfStartColumn_ThenReturnListWithOneStringRepresentingDateTimeInUtcTimeZone() {
        QueryResponseDTO result = getResultOfSelect("select start from joda_time order by start limit 1");
        List<String> titles = result.getTitles();
        List<List<String>> rows = result.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("start"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(rows, Matchers.contains(Arrays.asList("2018-08-23 00:00:00.0")));
    }

    @Test
    public void select_WhenConvertToUtcTimeZone_ThenReturnValuesAsTheyStoredInDatabase() {
        QueryResponseDTO selectResult = getResultOfSelect("select to_char_with_tz(start, 'YYYY-MM-DD HH24:MI:SS', 'UTC') as char_date_time_utc from joda_time order by start limit 3");
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("char_date_time_utc"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(3));
        MatcherAssert.assertThat(rows, Matchers.contains(
            Arrays.asList("2018-08-23 00:00:00"),
            Arrays.asList("2018-08-23 01:00:00"),
            Arrays.asList("2018-08-23 02:00:00")
        ));
    }

    @Test
    public void select_WhenConvertToMoscowTimeZone_ThenReturnValuesShiftedToMoscowTimeZone() {
        //time zone Europe/Moscow is +3 hours from UTC
        QueryResponseDTO selectResult = getResultOfSelect("select to_char_with_tz(start, 'YYYY-MM-DD HH24:MI:SS', 'Europe/Moscow') as char_date_time_moscow from joda_time order by start limit 3");
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("char_date_time_moscow"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(3));
        MatcherAssert.assertThat(rows, Matchers.contains(
            Arrays.asList("2018-08-23 03:00:00"),
            Arrays.asList("2018-08-23 04:00:00"),
            Arrays.asList("2018-08-23 05:00:00")
        ));
    }

    @Test
    public void select_WhenConvertToChicagoTimeZone_ThenReturnValuesShiftedToChicagoTimeZone() {
        //time zone America/Chicago is -5 hours from UTC
        QueryResponseDTO selectResult = getResultOfSelect("select to_char_with_tz(start, 'YYYY-MM-DD HH24:MI:SS', 'America/Chicago') as char_date_time_chicago from joda_time order by start limit 3");
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("char_date_time_chicago"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(3));
        MatcherAssert.assertThat(rows, Matchers.contains(
            Arrays.asList("2018-08-22 19:00:00"),
            Arrays.asList("2018-08-22 20:00:00"),
            Arrays.asList("2018-08-22 21:00:00")
        ));
    }

    @Test
    public void select_WhenConvertToUtcTimeZoneWithTimeFormat_ThenReturnNotShiftedValuesInExpectedTimeFormat() {
        QueryResponseDTO selectResult = getResultOfSelect("select to_char_with_tz(start, 'HH24:MI:SS', 'UTC') as char_date_time_utc from joda_time order by start limit 3");
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("char_date_time_utc"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(3));
        MatcherAssert.assertThat(rows, Matchers.contains(
            Arrays.asList("00:00:00"),
            Arrays.asList("01:00:00"),
            Arrays.asList("02:00:00")
        ));
    }

    @Test
    public void select_WhenConvertToMoscowTimeZoneWithTimeFormat_ThenReturnValuesShiftedToMoscowTimeZoneInExpectedTimeFormat() {
        //time zone Europe/Moscow is +3 hours from UTC
        QueryResponseDTO selectResult = getResultOfSelect("select to_char_with_tz(start, 'HH24:MI:SS', 'Europe/Moscow') as char_date_time_moscow from joda_time order by start limit 3");
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("char_date_time_moscow"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(3));
        MatcherAssert.assertThat(rows, Matchers.contains(
            Arrays.asList("03:00:00"),
            Arrays.asList("04:00:00"),
            Arrays.asList("05:00:00")
        ));
    }

    @Test
    public void select_WhenConvertToChicagoTimeZoneWithTimeFormat_ThenReturnValuesShiftedToChicagoTimeZoneInExpectedTimeFormat() {
        //time zone America/Chicago is -5 hours from UTC
        QueryResponseDTO selectResult = getResultOfSelect("select to_char_with_tz(start, 'HH24:MI:SS', 'America/Chicago') as char_date_time_chicago from joda_time order by start limit 3");
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("char_date_time_chicago"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(3));
        MatcherAssert.assertThat(rows, Matchers.contains(
            Arrays.asList("19:00:00"),
            Arrays.asList("20:00:00"),
            Arrays.asList("21:00:00")
        ));
    }

    @Test
    public void selectWithParams_WhenApplyDateParamWithoutCallingFunctionMinusTzOffset_ThenUseThatDateParamAsIsInUtcTimeZone() {
        List<QueryParam> params = Arrays.asList(
            new QueryParam("date", "2018-08-24", QueryParam.Type.DATE),
            new QueryParam("zone", "Europe/Samara", QueryParam.Type.STRING)
        );
        QueryRequestDTO request = new QueryRequestDTO(
            "select to_char_with_tz(start, 'YYYY-MM-DD HH24:MI:SS', @zone) from joda_time where start = @date",
            params
        );

        QueryResponseDTO selectResult = jdbcTemplateDemoService.selectWithParams(request);
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("to_char_with_tz"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(rows, Matchers.contains(Arrays.asList("2018-08-24 04:00:00")));
    }

    @Test
    public void selectWithParams_WhenSubtractUtcZoneOffsetFromDateParam_ThenDoNotChangeDateParam() {
        List<QueryParam> params = Arrays.asList(
            new QueryParam("date", "2018-08-24", QueryParam.Type.DATE),
            new QueryParam("zone1", "Europe/Samara", QueryParam.Type.STRING),
            new QueryParam("zone2", "UTC", QueryParam.Type.STRING)
        );
        QueryRequestDTO request = new QueryRequestDTO(
            "select to_char_with_tz(start, 'YYYY-MM-DD HH24:MI:SS', @zone1) from joda_time where start = minus_tz_offset(@date, @zone2)",
            params
        );

        QueryResponseDTO selectResult = jdbcTemplateDemoService.selectWithParams(request);
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("to_char_with_tz"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(rows, Matchers.contains(Arrays.asList("2018-08-24 04:00:00")));
    }

    @Test
    public void selectWithParams_WhenSubtractSamaraTimeZoneOffsetFromDateParam_ThenChangeDateParam() {
        List<QueryParam> params = Arrays.asList(
            new QueryParam("date", "2018-08-24", QueryParam.Type.DATE),
            new QueryParam("zone", "Europe/Samara", QueryParam.Type.STRING)
        );
        QueryRequestDTO request = new QueryRequestDTO(
            "select to_char_with_tz(start, 'YYYY-MM-DD HH24:MI:SS', @zone) from joda_time where start = minus_tz_offset(@date, @zone)",
            params
        );

        QueryResponseDTO selectResult = jdbcTemplateDemoService.selectWithParams(request);
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("to_char_with_tz"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(rows, Matchers.contains(Arrays.asList("2018-08-24 00:00:00")));
    }

    @Test
    public void selectWithParams_WhenSubtractSamaraTimeZoneOffsetFromDateParamAndFormatValuesToUtcZone_ThenApplyZoneOffsetAndReturnFormattedToUtcZoneValues() {
        List<QueryParam> params = Arrays.asList(
            new QueryParam("date", "2018-08-24", QueryParam.Type.DATE),
            new QueryParam("zone", "Europe/Samara", QueryParam.Type.STRING)
        );
        QueryRequestDTO request = new QueryRequestDTO(
            "select to_char_with_tz(start, 'YYYY-MM-DD HH24:MI:SS', 'UTC') from joda_time where start = minus_tz_offset(@date, @zone)",
            params
        );

        QueryResponseDTO selectResult = jdbcTemplateDemoService.selectWithParams(request);
        List<String> titles = selectResult.getTitles();
        List<List<String>> rows = selectResult.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("to_char_with_tz"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(rows, Matchers.contains(Arrays.asList("2018-08-23 20:00:00")));
    }
}