package edu.api;

import edu.Application;
import edu.persistence.model.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = {Application.class},
    webEnvironment = WebEnvironment.DEFINED_PORT)
public class ApiBookTest {

    private static final String API_ROOT = "http://localhost:8082/api/books";

    private static final RandomStringGenerator generator = new RandomStringGenerator
        .Builder()
        .withinRange('a', 'z')
        .build();

    private static final Random random = new Random(System.currentTimeMillis());


    private Book createRandomBook() {
        return new Book(generator.generate(10), generator.generate(10));
    }

    private String createBookAsUri(Book book) {
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllBooks_ThenOk() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetBooksByTitle_ThenOk() {
        Book book = createRandomBook();
        createBookAsUri(book);

        Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(book.getTitle(), response.jsonPath().getString("title"));
    }

    @Test
    public void whenGetCreatedBookById_ThenOk() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);

        Response response = RestAssured.get(location);
        Book createdBook = response.as(Book.class);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertNotNull(createdBook.getId());
        assertEquals(book.getAuthor(), createdBook.getAuthor());
        assertEquals(book.getTitle(), createdBook.getTitle());
    }

    @Test
    public void whenGetNotExistBookById_ThenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + random.nextInt());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewBook_ThenCreated() {
        Book book = createRandomBook();

        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewBook_ThenReturnCreatedBook() {
        Book book = createRandomBook();

        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(API_ROOT);

        Book createdBook = response.as(Book.class);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertNotNull(createdBook.getId());
        assertEquals(book.getAuthor(), createdBook.getAuthor());
        assertEquals(book.getTitle(), createdBook.getTitle());
    }

    @Test
    public void whenInvalidBook_ThenError() {
        Book book = createRandomBook();
        book.setAuthor(null);

        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedBook_ThenOk() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        book.setId(Long.parseLong(location.split("api/books/")[1]));
        book.setAuthor(generator.generate(10));
        book.setTitle(generator.generate(10));

        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(book, response.as(Book.class));
    }

    @Test
    public void whenUpdateCreatedBook_ThenReturnUpdatedBook() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        book.setId(Long.parseLong(location.split("api/books/")[1]));
        book.setAuthor(generator.generate(10));
        book.setTitle(generator.generate(10));

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .put(location);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(book, response.as(Book.class));
    }

    @Test
    public void whenDeleteCreatedBook_ThenOk() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);

        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenDeleteCreatedBook_ThenWhileGetByIdReturnNotFound() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);

        RestAssured.delete(location);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
