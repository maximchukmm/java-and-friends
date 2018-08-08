package edu.hibernate.basics;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.junit.Test;

import javax.persistence.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static edu.hibernate.basics.ParentAndChildAndParentChildMappingTest.Vehicle.Type.BICYCLE;
import static edu.hibernate.basics.ParentAndChildAndParentChildMappingTest.Vehicle.Type.UNICYCLE;
import static java.time.Month.*;
import static org.junit.Assert.*;

public class ParentAndChildAndParentChildMappingTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            TemporaryOwner.class,
            OwnerVehicleMapping.class,
            Vehicle.class
        };
    }

    private static String mappingId(String owner, Vehicle.Type vehicleType, Month month) {
        return owner + " - " + vehicleType + " - " + month;
    }

    private void prepareData() {
        doInTransaction(session -> {
            Vehicle bicycle = new Vehicle(BICYCLE);
            Vehicle unicycle = new Vehicle(UNICYCLE);

            TemporaryOwner jack = new TemporaryOwner("Jack");
            TemporaryOwner inna = new TemporaryOwner("Inna");

            OwnerVehicleMapping jackRentBicycleForApril = new OwnerVehicleMapping(jack, bicycle, APRIL);
            OwnerVehicleMapping jackRentUnicycleForMay = new OwnerVehicleMapping(jack, unicycle, MAY);
            OwnerVehicleMapping innaRentBicycleForAugust = new OwnerVehicleMapping(inna, bicycle, AUGUST);
            OwnerVehicleMapping innaRentUnicycleForSeptember = new OwnerVehicleMapping(inna, unicycle, SEPTEMBER);

            session.persist(bicycle);
            session.persist(unicycle);
            session.persist(jack);
            session.persist(inna);
            session.persist(jackRentBicycleForApril);
            session.persist(jackRentUnicycleForMay);
            session.persist(innaRentBicycleForAugust);
            session.persist(innaRentUnicycleForSeptember);
        });
    }

    @Test
    public void whenDeleteTemporaryOwner_ThenDeletingOfCompliantByForeignKeyMappingsIsDoneByJpa() {
        prepareData();

        doInTransaction(session -> {
            TemporaryOwner jack = session.find(TemporaryOwner.class, "Jack");
            session.remove(jack);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllJpa(session, OwnerVehicleMapping.class);

            assertFalse(
                ownerVehicleMappings
                    .stream()
                    .map(OwnerVehicleMapping::getId)
                    .anyMatch(id -> id.contains("Jack"))
            );
            assertTrue(
                ownerVehicleMappings
                    .stream()
                    .map(OwnerVehicleMapping::getId)
                    .allMatch(id -> id.contains("Inna"))
            );
        });
    }

    @Test
    public void whenDeleteSingleMapping_ThenOtherTablesContainSameAmountOfRecords() {
        prepareData();

        String id = mappingId("Jack", Vehicle.Type.BICYCLE, Month.APRIL);
        doInTransaction(session -> {
            OwnerVehicleMapping ownerVehicleMapping = session.find(OwnerVehicleMapping.class, id);
            session.remove(ownerVehicleMapping);
        });

        doInTransaction(session -> {
            List<TemporaryOwner> temporaryOwners = HibernateUtils.selectAllJpa(session, TemporaryOwner.class);
            assertEquals(2, temporaryOwners.size());

            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllJpa(session, OwnerVehicleMapping.class);
            assertEquals(3, ownerVehicleMappings.size());

            List<Vehicle> vehicles = HibernateUtils.selectAllJpa(session, Vehicle.class);
            assertEquals(2, vehicles.size());
        });
    }

    @Test
    public void whenDeleteVehicleWithOnDeleteCascadeActionToMapping_ThenDeletingOfCompliantByForeignKeyMappingsIsDoneByDB() {
        prepareData();

        doInTransaction(session -> {
            Vehicle bicycle = session.find(Vehicle.class, BICYCLE);
            session.remove(bicycle);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllJpa(session, OwnerVehicleMapping.class);

            assertTrue(
                ownerVehicleMappings
                    .stream()
                    .map(OwnerVehicleMapping::getId)
                    .noneMatch(id -> id.contains(BICYCLE.name()))
            );
        });
    }

    @Entity(name = "TemporaryOwner")
    @Table(name = "temporary_owner")
    @Getter
    @Setter
    @NoArgsConstructor
    static class TemporaryOwner {
        @Id
        private String id;

        @OneToMany(mappedBy = "temporaryOwner", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OwnerVehicleMapping> ownerVehicleMappings = new ArrayList<>();

        TemporaryOwner(String id) {
            this.id = id;
        }
    }

    @Entity(name = "OwnerVehicleMapping")
    @Table(name = "owner_vehicle_mapping")
    @Getter
    @Setter
    @NoArgsConstructor
    static class OwnerVehicleMapping {
        @Id
        private String id;

        @Basic(optional = false)
        @Enumerated(EnumType.STRING)
        private Month monthOfOwnership;

        @ManyToOne(optional = false)
        @JoinColumn(name = "temporary_owner_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_temporary_owner_id"))
        private TemporaryOwner temporaryOwner;

        @ManyToOne(optional = false)
        @JoinColumn(name = "vehicle_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_vehicle_id"))
        private Vehicle vehicle;

        OwnerVehicleMapping(TemporaryOwner temporaryOwner, Vehicle vehicle, Month monthOfOwnership) {
            this.id = mappingId(temporaryOwner.getId(), vehicle.getId(), monthOfOwnership);
            this.temporaryOwner = temporaryOwner;
            this.vehicle = vehicle;
            this.monthOfOwnership = monthOfOwnership;
        }
    }

    @Entity(name = "Vehicle")
    @Table(name = "vehicle")
    @Getter
    @Setter
    @NoArgsConstructor
    static class Vehicle {
        @Id
        @Enumerated(EnumType.STRING)
        private Type id;

        @OneToMany(mappedBy = "vehicle")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private List<OwnerVehicleMapping> ownerVehicleMappings = new ArrayList<>();

        Vehicle(Type id) {
            this.id = id;
        }

        enum Type {
            BICYCLE, UNICYCLE
        }
    }
}
