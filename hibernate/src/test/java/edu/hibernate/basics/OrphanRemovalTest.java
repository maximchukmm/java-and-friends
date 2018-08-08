package edu.hibernate.basics;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrphanRemovalTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            House.class,
            Furniture.class
        };
    }

    @Test
    public void orphanRemove_WhenRemoveEntityFromList_ThenRemoveEntityFromDatabase() {
        Long id = doInTransaction(session -> {
            House myHouse = new House("AMM");
            myHouse.addFurniture(new Furniture("chair"));
            myHouse.addFurniture(new Furniture("work desk"));
            myHouse.addFurniture(new Furniture("table"));
            myHouse.addFurniture(new Furniture("armchair"));
            session.persist(myHouse);
            return myHouse.getId();
        });

        doInTransaction(session -> {
            House house = session.find(House.class, id);
            Furniture chair = getFirstFurnitureWithTypeFromHouse("chair", house);
            house.removeFurniture(chair);
        });

        doInTransaction(session -> {
            List<Furniture> furniture = HibernateUtils.selectAllNative(session, Furniture.class);

            assertEquals(3, furniture.size());
        });
    }

    private Furniture getFirstFurnitureWithTypeFromHouse(String furnitureType, House house) {
        return house.getFurniture()
            .stream()
            .filter(furniture -> furniture.getType().equals(furnitureType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    @Entity(name = "House")
    @Table(name = "house")
    @Data
    @NoArgsConstructor
    static class House {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String address;

        @OneToMany(mappedBy = "house", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        List<Furniture> furniture = new ArrayList<>();

        House(String address) {
            this.address = address;
        }

        void addFurniture(Furniture furniture) {
            furniture.setHouse(this);
            this.furniture.add(furniture);
        }

        void removeFurniture(Furniture furniture) {
            this.furniture.remove(furniture);
            furniture.setHouse(null);
        }
    }

    @Entity(name = "Furniture")
    @Table(name = "furniture")
    @Data
    @NoArgsConstructor
    static class Furniture {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String type;

        @ManyToOne(fetch = FetchType.LAZY, cascade = {})
        @JoinColumn(name = "house_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_house_id"))
        private House house;

        Furniture(String type) {
            this.type = type;
        }
    }
}
