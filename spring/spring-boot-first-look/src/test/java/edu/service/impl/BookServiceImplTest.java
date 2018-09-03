package edu.service.impl;

import edu.persistence.model.Book;
import edu.persistence.repo.BookRepository;
import edu.service.iface.BookService;
import edu.web.dto.AuthorsBooksDTO;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    private BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private BookService bookService = new BookServiceImpl(bookRepository);

    @Test
    public void getBooksGroupedByAuthor_WhenBooksFoundForMultipleAuthors() {
        when(bookRepository.findAll()).thenReturn(books());

        Map<String, List<Book>> actualBooksGroupedByAuthor = bookService.getBooksGroupedByAuthor();
        Map<String, List<Book>> expectedBooksGroupedByAuthor = groupByAuthor(books());

        assertEquals(expectedBooksGroupedByAuthor, actualBooksGroupedByAuthor);
    }

    @Test
    public void authorsBooks_WhenNoBooksFound() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        AuthorsBooksDTO result = bookService.findAuthorsBooks();

        Assert.assertEquals(new AuthorsBooksDTO(Collections.emptyMap()), result);
    }

    @Test
    public void authorsBooks_WhenBooksFoundForOneAuthor() {
        List<Book> books = Arrays.asList(
            new Book("Title 1", "Author 1"),
            new Book("Title 2", "Author 1")
        );
        when(bookRepository.findAll()).thenReturn(books);

        AuthorsBooksDTO result = bookService.findAuthorsBooks();

        AuthorsBooksDTO expectedResult = new AuthorsBooksDTO(groupByAuthor(books));
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void authorsBooks_WhenBooksFoundForMultipleAuthors() {
        List<Book> books = books();
        when(bookRepository.findAll()).thenReturn(books);

        AuthorsBooksDTO result = bookService.findAuthorsBooks();

        AuthorsBooksDTO expectedResult = new AuthorsBooksDTO(groupByAuthor(books));
        Assert.assertEquals(expectedResult, result);
    }

    private List<Book> books() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Title 1", "Author 1"));
        books.add(new Book(2L, "Title 2", "Author 2"));
        books.add(new Book(3L, "Title 3", "Author 2"));
        books.add(new Book(4L, "Title 4", "Author 3"));
        books.add(new Book(5L, "Title 5", "Author 3"));
        books.add(new Book(6L, "Title 6", "Author 3"));
        return books;
    }

    private Map<String, List<Book>> groupByAuthor(List<Book> books) {
        Map<String, List<Book>> groupedByAuthor = new HashMap<>();
        for (Book book : books) {
            String author = book.getAuthor();
            groupedByAuthor.putIfAbsent(author, new ArrayList<>());
            groupedByAuthor.get(author).add(book);
        }
        return groupedByAuthor;
    }
}
