package edu.hibernate.relationships;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceException;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

public class ParentOneToOneChildParentOneToManyTest extends HibernateBaseTest {

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            ParentOneToOne.class,
            ParentOneToMany.class,
            Child.class
        };
    }

    @Test(expected = PersistenceException.class)
    public void givenParentOneToOneWithoutAnyCascading_WhenTryingToDeleteParentOneToOne_ThenThrowPersistenceException() {
        doInTransaction(session -> {
            ParentOneToOne parentOneToOne1 = session
                .createQuery("select p from ParentOneToOne p where p.name = 'ParentOneToOne_1'", ParentOneToOne.class)
                .getSingleResult();

            session.remove(parentOneToOne1);
        });
    }

    @Test
    public void WhenSelectAllParentOneToOne_ThenGetNPlusOneQueryProblem() {
        doInTransaction(session -> {
            List<ParentOneToOne> parentOneToOnes = HibernateUtils.selectAllJpql(session, ParentOneToOne.class);

            parentOneToOnes.forEach(System.out::println);
        });
    }

    @Test
    public void WhenJoinFetchChildAndParentOneToManyWhileSelectingAllParentOneToOne_ThenGenerateSingleSelect() {
        doInTransaction(session -> {
            List<ParentOneToOne> parentOneToOnes
                = session.createQuery(
                "select p from ParentOneToOne p join fetch p.child c join fetch c.parentOneToMany",
                ParentOneToOne.class).getResultList();

            parentOneToOnes.forEach(System.out::println);
        });
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            ParentOneToMany parentOneToMany1 = new ParentOneToMany("ParentOneToMany_1");
            ParentOneToMany parentOneToMany2 = new ParentOneToMany("ParentOneToMany_2");

            ParentOneToOne parentOneToOne1 = new ParentOneToOne("ParentOneToOne_1");
            ParentOneToOne parentOneToOne2 = new ParentOneToOne("ParentOneToOne_2");

            Child child1 = new Child("child_1");
            child1.setParentOneToMany(parentOneToMany1);
            child1.setParentOneToOne(parentOneToOne1);

            Child child2 = new Child("child_2");
            child2.setParentOneToMany(parentOneToMany1);
            child2.setParentOneToOne(parentOneToOne2);

            session.persist(parentOneToOne1);
            session.persist(parentOneToOne2);
            session.persist(parentOneToMany1);
            session.persist(parentOneToMany2);
        });
    }

    @Entity(name = "ParentOneToOne")
    @Table(name = "parent_one_to_one")
    @Data
    @NoArgsConstructor
    private static class ParentOneToOne {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(nullable = false)
        private String name;

        @OneToOne(mappedBy = "parentOneToOne")
        private Child child;

        public ParentOneToOne(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ParentOneToOne)) return false;

            ParentOneToOne that = (ParentOneToOne) o;

            return that.id != null ? that.id.equals(that.getId()) : that.getId() == null;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "ParentOneToOne{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
        }
    }

    @Entity(name = "ParentOneToMany")
    @Table(name = "parent_one_to_many")
    @Data
    @NoArgsConstructor
    private static class ParentOneToMany {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(nullable = false)
        private String name;

        public ParentOneToMany(String name) {
            this.name = name;
            this.children = new ArrayList<>();
        }

        @OneToMany(
            mappedBy = "parentOneToMany",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
        private List<Child> children;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ParentOneToMany)) return false;

            ParentOneToMany that = (ParentOneToMany) o;

            return id != null ? id.equals(that.getId()) : that.getId() == null;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "ParentOneToMany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
        }
    }

    @Entity(name = "Child")
    @Table(name = "child")
    @Data
    @NoArgsConstructor
    private static class Child {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(nullable = false, unique = true)
        private String name;

        public Child(String name) {
            this.name = name;
        }

        @OneToOne
        @JoinColumn(
            name = "parent_one_to_one_id",
            referencedColumnName = "id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_child_parent_one_to_one_id"))
//        @OnDelete(action = OnDeleteAction.CASCADE)
        private ParentOneToOne parentOneToOne;

        @ManyToOne
        @JoinColumn(
            name = "parent_one_to_many_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_child_parent_one_to_many_id"))
        private ParentOneToMany parentOneToMany;

        public void setParentOneToOne(ParentOneToOne parentOneToOne) {
            this.parentOneToOne = parentOneToOne;
            this.parentOneToOne.setChild(this);
        }

        public void setParentOneToMany(ParentOneToMany parentOneToMany) {
            if (this.parentOneToMany != null)
                this.parentOneToMany.getChildren().remove(this);

            this.parentOneToMany = parentOneToMany;
            if (!this.parentOneToMany.getChildren().contains(this))
                this.parentOneToMany.getChildren().add(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Child)) return false;

            Child child = (Child) o;

            if (id != null ? !id.equals(child.id) : child.id != null) return false;
            return name.equals(child.name);
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentOneToOne.name=" + parentOneToOne.getName() +
                ", parentOneToMany.name=" + parentOneToMany.getName() +
                '}';
        }
    }
}
