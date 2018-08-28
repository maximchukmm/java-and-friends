package edu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class QueryResponseDTO {
    private List<String> titles = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();
}
