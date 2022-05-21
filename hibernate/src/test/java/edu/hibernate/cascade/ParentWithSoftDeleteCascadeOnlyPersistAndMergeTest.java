package edu.hibernate.cascade;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static edu.hibernate.cascade.ParentWithSoftDeleteCascadeOnlyPersistAndMergeTest.Alphabet.Type.ENG;

public class ParentWithSoftDeleteCascadeOnlyPersistAndMergeTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Alphabet.class,
            Letter.class
        };
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            Alphabet alphabet = new Alphabet(1L);
            long id = 1;
            for (char c = 'A'; c <= 'Z'; c++) {
                Letter letter = new Letter(id++, c);
                letter.setAlphabet(alphabet);
                alphabet.getLetters().add(letter);
            }
            session.persist(alphabet);
        });
    }

    @Test
    public void whenRemoveAlphabet_ThenLettersDoNotChanged() {
        doInTransaction(session -> {
            Alphabet alphabet = session.find(Alphabet.class, 1L);
            session.remove(alphabet);
        });

        doInTransaction(session -> {
            List<Letter> letters = HibernateUtils.selectAllJpql(session, Letter.class);

            Assert.assertEquals(ENG.getNumberOfLetters(), letters.size());
        });
    }

    @Test
    public void whenRemoveLetterFromList_ThenLetterDoNotRemovedFromDatabase() {
        doInTransaction(session -> {
            Alphabet alphabet = session.find(Alphabet.class, 1L);
            alphabet.getLetters().remove(0);
            session.merge(alphabet);
        });

        doInTransaction(session -> {
            Alphabet alphabet = session.find(Alphabet.class, 1L);

            Assert.assertEquals(ENG.getNumberOfLetters(), alphabet.getLetters().size());
        });
    }

    @Test
    public void whenMergeExistingAlphabetWithNewLetters_ThenDoNotRemoveOldLettersAndInsertNewLetters() {
        List<Letter> newLetters = doInTransaction(session -> {
            Alphabet alphabet = new Alphabet(1L);
            List<Letter> letters = new ArrayList<>();
            letters.add(new Letter(100L, 'a'));
            letters.add(new Letter(101L, 'b'));
            letters.add(new Letter(102L, 'c'));
            letters.forEach(letter -> letter.setAlphabet(alphabet));
            alphabet.setLetters(letters);
            session.merge(alphabet);
            return letters;
        });

        doInTransaction(session -> {
            Alphabet alphabet = session.find(Alphabet.class, 1L);

            Assert.assertEquals(newLetters.size() + ENG.getNumberOfLetters(), alphabet.getLetters().size());
        });
    }

    @Test
    public void whenMergeExistingAlphabetWithNewLettersAndExplicitlyRemoveOldLetters_ThenAfterThatAlphabetContainsOnlyNewLetters() {
        List<Letter> newLetters = doInTransaction(session -> {
            List<Letter> oldLetters = session
                .createQuery("select l from Letter l where l.alphabet.id = :alphabetId", Letter.class)
                .setParameter("alphabetId", 1L)
                .getResultList();
            oldLetters.forEach(oldLetter -> {
                oldLetter.setAlphabet(null);
                session.remove(oldLetter);
            });
            session.flush();

            Alphabet alphabet = new Alphabet(1L);
            List<Letter> letters = new ArrayList<>();
            letters.add(new Letter(100L, 'a'));
            letters.add(new Letter(101L, 'b'));
            letters.add(new Letter(102L, 'c'));
            letters.forEach(letter -> letter.setAlphabet(alphabet));
            alphabet.setLetters(letters);
            session.merge(alphabet);
            return letters;
        });

        doInTransaction(session -> {
            Alphabet alphabet = session.find(Alphabet.class, 1L);

            Assert.assertEquals(newLetters.size(), alphabet.getLetters().size());
        });
    }

    @Entity(name = "Alphabet")
    @Table(name = "alphabet")
    @SQLDelete(sql = "UPDATE alphabet SET deleted = true WHERE id = ?")
    @Data
    @NoArgsConstructor
    static class Alphabet {
        @Id
        private Long id;

        @Enumerated(value = EnumType.STRING)
        private Type type = ENG;

        @OneToMany(mappedBy = "alphabet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private List<Letter> letters = new ArrayList<>();

        private boolean deleted;

        Alphabet(Long id) {
            this.id = id;
        }

        enum Type {
            ENG(26);

            private int numberOfLetters;

            Type(int numberOfLetters) {
                this.numberOfLetters = numberOfLetters;
            }

            int getNumberOfLetters() {
                return numberOfLetters;
            }
        }
    }

    @Entity(name = "Letter")
    @Table(name = "letter")
    @Data
    @NoArgsConstructor
    static class Letter {
        @Id
        private Long id;

        private char letter;

        @ManyToOne(optional = false)
        @JoinColumn(name = "alphabet_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_alphabet_id"))
        private Alphabet alphabet;

        Letter(Long id, char letter) {
            this.id = id;
            this.letter = letter;
        }
    }
}
