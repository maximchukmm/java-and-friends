package edu.service.impl;

import edu.persistence.model.Book;
import edu.persistence.repo.BookRepository;
import edu.service.iface.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class BookServiceImplTest {

    @TestConfiguration
    static class BookServiceImplTestContextConfiguration {
        @Bean
        public BookService bookService() {
            return new BookServiceImpl();
        }
    }

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private static List<Book> books() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Title 1", "Author 1"));
        books.add(new Book(2L, "Title 2", "Author 2"));
        books.add(new Book(3L, "Title 3", "Author 2"));
        books.add(new Book(4L, "Title 4", "Author 3"));
        books.add(new Book(5L, "Title 5", "Author 3"));
        books.add(new Book(6L, "Title 6", "Author 3"));
        return books;
    }

    private static Map<String, List<Book>> groupByAuthor(List<Book> books) {
        Map<String, List<Book>> groupedByAuthor = new HashMap<>();
        for (Book book : books) {
            String author = book.getAuthor();
            groupedByAuthor.putIfAbsent(author, new ArrayList<>());
            groupedByAuthor.get(author).add(book);
        }
        return groupedByAuthor;
    }

    @Test
    public void getBooksGroupedByAuthor_when_Then1() {
        Mockito.when(bookRepository.findAll()).thenReturn(books());

        Map<String, List<Book>> actualBooksGroupedByAuthor = bookService.getBooksGroupedByAuthor();
        Map<String, List<Book>> expectedBooksGroupedByAuthor = groupByAuthor(books());

        assertEquals(expectedBooksGroupedByAuthor, actualBooksGroupedByAuthor);
    }
}
