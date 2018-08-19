package edu.persistence.repo;

import edu.persistence.model.Book;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    private static final RandomStringGenerator generator = new RandomStringGenerator
        .Builder()
        .withinRange('a', 'z')
        .build();

    private Book createRandomBook() {
        return new Book(generator.generate(10), generator.generate(10));
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindByExistingTitle_ThenReturnBook() {
        Book persistedBook = entityManager.persistAndFlush(createRandomBook());

        Book foundBook = bookRepository.findByTitle(persistedBook.getTitle());

        assertThat(foundBook).isEqualTo(persistedBook);
    }

    @Test
    public void whenFindByNonExistingTitle_ThenReturnNull() {
        Book foundBook = bookRepository.findByTitle(generator.generate(10));

        assertThat(foundBook).isNull();
    }

    @Test
    public void whenFindByNullTitle_ThenReturnNull() {
        Book foundBook = bookRepository.findByTitle(null);

        assertThat(foundBook).isNull();
    }
}
