package edu.service.impl;

import edu.persistence.model.Book;
import edu.persistence.repo.BookRepository;
import edu.service.iface.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Map<String, List<Book>> getBooksGroupedByAuthor() {
        return Streamable
            .of(bookRepository.findAll())
            .stream()
            .collect(Collectors.groupingBy(Book::getAuthor));
    }
}
