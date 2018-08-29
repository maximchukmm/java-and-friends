package edu.service;

import edu.dto.QueryRequestDTO;
import edu.dto.QueryResponseDTO;

public interface JdbcTemplateDemoService {
    QueryResponseDTO select(QueryRequestDTO request);

    QueryResponseDTO selectWithParams(QueryRequestDTO request);
}
