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

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {BookController.class}, secure = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void create_WhenEmptyBody() throws Exception {
        mvc.perform(post("/api/books").content("{  }").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void create_WhenEmptyAuthor() throws Exception {
        mvc.perform(post("/api/books").content("{ \"title\": \"foo\", \"author\": \"\" }").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void create_WhenBlankAuthor() throws Exception {
        mvc.perform(post("/api/books").content("{ \"title\": \"foo\", \"author\": \"  \" }").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

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
            .andExpect(jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void findByTitle_WhenFindByExistingTitle_ThenOk() throws Exception {
        Mockito.when(bookRepository.findByTitle("Title 1"))
            .thenReturn(new Book(1L, "Title 1", "Author 1"));

        mvc.perform(
            get("/api/books/title/Title 1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void findByTitle_WhenFindByNonExistingTitle_ThenOk() throws Exception {
        Mockito.when(bookRepository.findByTitle("Title 1"))
            .thenReturn(null);

        mvc.perform(
            get("/api/books/title/Title 1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
