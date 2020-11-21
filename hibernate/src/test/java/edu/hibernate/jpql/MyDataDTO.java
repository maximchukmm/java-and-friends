package edu.hibernate.jpql;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyDataDTO {
    private String title;
    private Long count;
}
