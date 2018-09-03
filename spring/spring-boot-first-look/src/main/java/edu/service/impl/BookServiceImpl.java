package edu.service.impl;

import edu.persistence.model.Book;
import edu.persistence.repo.BookRepository;
import edu.service.iface.BookService;
import edu.web.dto.AuthorsBooksDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Map<String, List<Book>> getBooksGroupedByAuthor() {
        return Streamable
            .of(bookRepository.findAll())
            .stream()
            .collect(Collectors.groupingBy(Book::getAuthor));
    }

    @Override
    public AuthorsBooksDTO findAuthorsBooks() {
        Map<String, List<Book>> authorsBooks = Streamable
            .of(bookRepository.findAll())
            .stream()
            .collect(Collectors.groupingBy(Book::getAuthor));
        return new AuthorsBooksDTO(authorsBooks);
    }
}
