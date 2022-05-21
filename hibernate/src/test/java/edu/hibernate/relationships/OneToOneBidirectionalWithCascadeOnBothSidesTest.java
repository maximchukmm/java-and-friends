package edu.hibernate.relationships;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OneToOneBidirectionalWithCascadeOnBothSidesTest extends HibernateBaseTest {

    @Test
    public void whenRemovePerson_ThenAlsoRemoveHisPassportBecauseOfCascadeSetToCascadeAll() {
        Long personId = doInTransaction(session -> {
            Person person = new Person("Paul");
            Passport passport = new Passport(person, "1234");
            person.setPassport(passport);
            session.persist(person);

            return person.getId();
        });

        doInTransaction(session -> {
            Person person = session.find(Person.class, personId);

            session.remove(person);
        });

        doInTransaction(session -> {
            List<Passport> passports = session.createQuery("select p from Passport p", Passport.class).getResultList();

            assertEquals(0, passports.size());
        });
    }

    @Test
    public void whenRemovePassport_ThenAlsoRemoveAssociatedWithItPersonBecauseOfCascadeSetToCascadeAll() {
        Long passportId = doInTransaction(session -> {
            Person person = new Person("Paul");
            Passport passport = new Passport(person, "1234");
            person.setPassport(passport);
            session.persist(person);

            return passport.getId();
        });

        doInTransaction(session -> {
            Passport passport = session.find(Passport.class, passportId);

            session.remove(passport);
        });

        doInTransaction(session -> {
            List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();

            assertEquals(0, people.size());
        });
    }

    @Test
    public void whenRemovePassportStorage_ThenRemoveItsPassportsAndAlsoRemovePeopleWithThesePassports() {
        Long passportsStorageId = doInTransaction(session -> {
            Person person = new Person("Paul");
            session.persist(person);

            PassportsStorage passportsStorage = new PassportsStorage("General storage");
            session.persist(passportsStorage);

            Passport passport = new Passport(passportsStorage, person, "1234");
            passportsStorage.getPassports().add(passport);
            session.merge(passportsStorage);

            return passportsStorage.getId();
        });

        doInTransaction(session -> {
            PassportsStorage passportsStorage = session.find(PassportsStorage.class, passportsStorageId);
            session.remove(passportsStorage);
        });

        doInTransaction(session -> {
            List<PassportsStorage> passportsStorages = session.createQuery("select ps from PassportsStorage ps", PassportsStorage.class).getResultList();
            List<Passport> passports = session.createQuery("select p from Passport p", Passport.class).getResultList();
            List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();

            assertTrue(passportsStorages.isEmpty());
            assertTrue(passports.isEmpty());
            assertTrue(people.isEmpty());
        });
    }

    @Test
    public void whenClearPassportCollectionOfPassportStorageAndMergeThatStorage_ThenRemovePassportsAndPeopleWithThesePassports() {
        Long passportsStorageId = doInTransaction(session -> {
            Person person = new Person("Paul");
            session.persist(person);

            PassportsStorage passportsStorage = new PassportsStorage("General storage");
            session.persist(passportsStorage);

            Passport passport = new Passport(passportsStorage, person, "1234");
            passportsStorage.getPassports().add(passport);
            session.merge(passportsStorage);

            return passportsStorage.getId();
        });

        doInTransaction(session -> {
            PassportsStorage passportsStorage = session.find(PassportsStorage.class, passportsStorageId);
            passportsStorage.getPassports().clear();
            session.merge(passportsStorage);
        });

        doInTransaction(session -> {
            List<PassportsStorage> passportsStorages = session.createQuery("select ps from PassportsStorage ps", PassportsStorage.class).getResultList();
            List<Passport> passports = session.createQuery("select p from Passport p", Passport.class).getResultList();
            List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();

            assertFalse(passportsStorages.isEmpty());
            assertTrue(passports.isEmpty());
            assertTrue(people.isEmpty());
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Person.class,
            Passport.class,
            PassportsStorage.class
        };
    }

    @Entity(name = "Person")
    @Table(name = "person")
    @NoArgsConstructor
    @Getter
    @Setter
    static class Person {
        @Id
        @GeneratedValue
        private Long id;

        @Basic(optional = false)
        private String name;

        @OneToOne(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL)
        private Passport passport;

        Person(String name) {
            this.name = name;
        }
    }

    @Entity(name = "Passport")
    @Table(name = "passport")
    @NoArgsConstructor
    @Getter
    @Setter
    static class Passport {
        @Id
        @GeneratedValue
        private Long id;

        @Basic(optional = false)
        private String code;

        @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
        @JoinColumn(name = "person_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_person_id"))
        private Person person;

        @ManyToOne(optional = true)
        @JoinColumn(name = "passport_storage_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_passport_storage_id"))
        private PassportsStorage passportsStorage;

        Passport(Person person, String code) {
            this.person = person;
            this.code = code;
        }

        Passport(PassportsStorage passportsStorage, Person person, String code) {
            this(person, code);
            this.passportsStorage = passportsStorage;
        }
    }

    @Entity(name = "PassportsStorage")
    @Table(name = "passports_storage")
    @NoArgsConstructor
    @Getter
    @Setter
    static class PassportsStorage {
        @Id
        @GeneratedValue
        private Long id;

        @Basic(optional = false)
        private String name;

//        @OneToMany(mappedBy = "passportsStorage", cascade = CascadeType.ALL)
        @OneToMany(mappedBy = "passportsStorage", orphanRemoval = true, cascade = CascadeType.ALL)
        private List<Passport> passports;

        PassportsStorage(String name) {
            this.name = name;
            this.passports = new ArrayList<>();
        }
    }
}
