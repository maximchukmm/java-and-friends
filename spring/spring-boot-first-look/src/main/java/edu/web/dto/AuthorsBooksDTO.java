package edu.web.dto;

import edu.persistence.model.Book;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@EqualsAndHashCode
public class AuthorsBooksDTO {
    private Map<String, List<Book>> authorsBooks;
}
