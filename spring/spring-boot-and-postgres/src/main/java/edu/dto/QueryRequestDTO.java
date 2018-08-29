package edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequestDTO {
    private String query;
    private List<QueryParam> params;

    public QueryRequestDTO(String query) {
        this.query = query;
    }
}
