package edu.hibernate.cascade;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ParentWithoutCascadeTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Parent.class,
            Child.class
        };
    }

    @Test(expected = PersistenceException.class)
    public void whenDeleteParentWithoutCascadingWithChildThatCannotExistsWithoutParent_ThenThrowException() {
        Long id = doInTransaction(session -> {
            Parent parent = new Parent();
            session.persist(parent);
            Child child = new Child();
            child.setParent(parent);
            session.persist(child);
            return parent.getId();
        });

        doInTransaction(session -> {
            Parent parent = session.find(Parent.class, id);
            session.remove(parent);
        });
    }

    @Entity(name = "Parent")
    @Table(name = "parent")
    @Data
    @NoArgsConstructor
    static class Parent {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @OneToMany(mappedBy = "parent", cascade = {})
        List<Child> children = new ArrayList<>();
    }

    @Entity(name = "Child")
    @Table(name = "child")
    @Data
    @NoArgsConstructor
    static class Child {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne(optional = false, cascade = {})
        @JoinColumn(name = "parent_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_parent_id"))
        private Parent parent;
    }
}