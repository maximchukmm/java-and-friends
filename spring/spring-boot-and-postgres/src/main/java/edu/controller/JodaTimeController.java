package edu.controller;

import edu.model.JodaTime;
import edu.repository.JodaTimeRepository;
import edu.service.JdbcTemplateDemoService;
import edu.service.QueryDTO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/joda-time")
public class JodaTimeController {

    private JodaTimeRepository jodaTimeRepository;
    private JdbcTemplateDemoService jdbcTemplateDemoService;

    @Autowired
    public JodaTimeController(
        JodaTimeRepository jodaTimeRepository,
        JdbcTemplateDemoService jdbcTemplateDemoService
    ) {
        this.jodaTimeRepository = jodaTimeRepository;
        this.jdbcTemplateDemoService = jdbcTemplateDemoService;
    }

    @PostMapping("/init")
    @ResponseStatus(HttpStatus.CREATED)
    public void init() {
        DateTime dateTime = new DateTime(2018, 8, 23, 5, 0, 0, 0, DateTimeZone.forID("Europe/Moscow"));
        for (int i = 0; i < 10; i++) {
            jodaTimeRepository.save(new JodaTime(dateTime.plusHours(i)));
        }
        jodaTimeRepository.flush();
    }

    @PostMapping("/select")
    public void select(@RequestBody QueryDTO select) {
        jdbcTemplateDemoService.executeAndLogSelectQuery(select.getQuery());
    }

    @DeleteMapping("/delete-all")
    public void deleteAll() {
        jodaTimeRepository.deleteAll();
        jodaTimeRepository.flush();
    }
}
