package edu.controller;

import edu.dto.QueryRequestDTO;
import edu.dto.QueryResponseDTO;
import edu.service.JdbcTemplateDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/joda-time")
public class JodaTimeController {

    private JdbcTemplateDemoService jdbcTemplateDemoService;

    @Autowired
    public JodaTimeController(
        JdbcTemplateDemoService jdbcTemplateDemoService
    ) {
        this.jdbcTemplateDemoService = jdbcTemplateDemoService;
    }

    @PostMapping("/select")
    public QueryResponseDTO select(@RequestBody QueryRequestDTO select) {
        return jdbcTemplateDemoService.select(select);
    }
}
