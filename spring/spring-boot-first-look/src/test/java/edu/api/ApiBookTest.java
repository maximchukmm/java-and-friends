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

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = {Application.class},
    webEnvironment = WebEnvironment.DEFINED_PORT)
public class ApiBookTest {

    private static final String API_ROOT = "http://localhost:8082/api/books";
    private static final RandomStringGenerator generator = new RandomStringGenerator.Builder()
        .withinRange('a', 'z')
        .build();

    private Book createRandomBook() {
        return new Book(generator.generate(3), generator.generate(5));
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
}
