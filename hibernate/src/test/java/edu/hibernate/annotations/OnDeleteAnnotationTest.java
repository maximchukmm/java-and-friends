package edu.hibernate.annotations;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.junit.Test;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class OnDeleteAnnotationTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Parent.class,
            Child.class
        };
    }

    @Test
    public void whenParentWithOnDeleteAnnotationWithCascadeForChildRelationIsRemoved_ThenCascadeDeleteOfChildrenWillBeHandledByDBByAddingOnDeleteCascadeConstraint() {
        Long parentId = doInTransaction(session -> {
            Parent parent = new Parent();
            parent.addChild(new Child());
            parent.addChild(new Child());
            parent.addChild(new Child());

            session.persist(parent);
            return parent.getId();
        });

        doInTransaction(session -> {
            Parent parent = session.find(Parent.class, parentId);
            session.remove(parent);
        });

        doInTransaction(session -> {
            List<Child> children = HibernateUtils.selectAllJpql(session, Child.class);

            assertTrue(children.isEmpty());
        });

        doInTransaction(session -> {
            List<Child> children = HibernateUtils.selectAllNative(session, Child.class);

            assertTrue(children.isEmpty());
        });
    }

    @Entity(name = "Parent")
    @Table(name = "parent")
    @Getter
    @Setter
    @NoArgsConstructor
    static class Parent {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private List<Child> children = new ArrayList<>();

        void addChild(Child child) {
            children.add(child);
            child.setParent(this);
        }

        void removeChild(Child child) {
            children.remove(child);
            child.setParent(null);
        }
    }

    @Entity(name = "Child")
    @Table(name = "child")
    @Getter
    @Setter
    @NoArgsConstructor
    static class Child {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_parent_id"))
        private Parent parent;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Child)) return false;
            Child child = (Child) o;
            return id != null && id.equals(child.id);
        }

        @Override
        public int hashCode() {
            return 31;
        }
    }
}
