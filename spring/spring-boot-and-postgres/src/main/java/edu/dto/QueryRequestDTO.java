package edu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryRequestDTO {
    private String query;

    public QueryRequestDTO(String query) {
        this.query = query;
    }
}
