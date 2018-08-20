package edu.web.controller;

import edu.persistence.model.Book;
import edu.persistence.repo.BookRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

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
            MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
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
            MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void findByTitle_WhenFindByExistingTitle_ThenOk() throws Exception {
        Mockito.when(bookRepository.findByTitle("Title 1"))
            .thenReturn(new Book(1L, "Title 1", "Author 1"));

        mvc.perform(
            MockMvcRequestBuilders.get("/api/books/title/Title 1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findByTitle_WhenFindByNonExistingTitle_ThenOk() throws Exception {
        Mockito.when(bookRepository.findByTitle("Title 1"))
            .thenReturn(null);

        mvc.perform(
            MockMvcRequestBuilders.get("/api/books/title/Title 1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
