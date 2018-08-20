package edu.web.controller;

import edu.persistence.model.Book;
import edu.persistence.repo.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {BookController.class}, secure = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void findAll_WhenFindAllBooks_ThenOk() throws Exception {
        mvc.perform(
            get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void findAll_WhenFindAllBooks_ThenReturnAllAvailableBooks() throws Exception {
        List<Book> books = Arrays.asList(
            new Book(1L, "Title 1", "Author 1"),
            new Book(2L, "Title 2", "Author 2"),
            new Book(3L, "Title 3", "Author 3")
        );
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        mvc.perform(
            get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }
}
