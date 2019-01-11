package edu.hibernate.tablegeneration;

import edu.hibernate.base.HibernateBaseTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

public class OneToOneWithImplicitUniqueConstraintTest extends HibernateBaseTest {

    @Test
    public void whenOneToOneWithNonOptionalOnOwnerSide_ThenImplicitlyGenerateUniqueConstraintForColumn() {
        doInTransaction(session -> {
            @SuppressWarnings("unchecked")
            List<String> constraints = (List<String>) session
                .createNativeQuery("SELECT CONSTRAINT_TYPE || ' ' || COLUMN_LIST FROM INFORMATION_SCHEMA.CONSTRAINTS WHERE TABLE_NAME = 'PASSPORT'")
                .getResultList();

            List<String> uniqueConstraintOnPersonIdColumn = constraints
                .stream()
                .filter(constraint -> constraint.toLowerCase().contains("unique") && constraint.toLowerCase().contains("person_id"))
                .collect(Collectors.toList());

            Assert.assertEquals(1, uniqueConstraintOnPersonIdColumn.size());
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Person.class,
            Passport.class
        };
    }

    @Entity(name = "Person")
    @Table(name = "person")
    static class Person {
        @Id
        private Long id;

        @Basic(optional = false)
        private String name;

        @OneToOne(mappedBy = "person")
        private Passport passport;
    }

    @Entity(name = "Passport")
    @Table(name = "passport")
    static class Passport {
        @Id
        private Long id;

        @Basic(optional = false)
        private String code;

        @OneToOne(optional = false)
        @JoinColumn(name = "person_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_person_id"))
        private Person person;
    }
}
