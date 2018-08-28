package edu.service;

import edu.dto.QueryRequestDTO;
import edu.dto.QueryResponseDTO;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTemplateDemoServiceTest {
    @Autowired
    private JdbcTemplateDemoService jdbcTemplateDemoService;

    @Test
    public void select_whenSelectFirstValueOfStartColumn_ThenReturnListWithOneStringRepresentingDateTimeInUtcTimeZone() {
        QueryRequestDTO request = new QueryRequestDTO("select start from joda_time order by start limit 1");

        QueryResponseDTO result = jdbcTemplateDemoService.select(request);
        List<String> titles = result.getTitles();
        List<List<String>> rows = result.getRows();

        MatcherAssert.assertThat(titles, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(titles, Matchers.contains("start"));
        MatcherAssert.assertThat(rows, IsCollectionWithSize.hasSize(1));
        MatcherAssert.assertThat(rows, Matchers.contains(Arrays.asList("2018-08-23 00:00:00.0")));
    }
}