package edu.service.iface;

import edu.persistence.model.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    Map<String, List<Book>> getBooksGroupedByAuthor();
}
