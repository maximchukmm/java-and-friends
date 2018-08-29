package edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParam {
    private String name;
    private String value;
    private Type type;

    public enum Type {
        DATE, STRING
    }
}
