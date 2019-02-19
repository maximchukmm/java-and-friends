package edu.hibernate.softdelete.experiments;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static edu.hibernate.softdelete.experiments.SoftDeleteParentAndChildAndParentChildMappingTest2.Vehicle.Type.BICYCLE;
import static edu.hibernate.softdelete.experiments.SoftDeleteParentAndChildAndParentChildMappingTest2.Vehicle.Type.UNICYCLE;
import static java.time.Month.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SoftDeleteParentAndChildAndParentChildMappingTest2 extends HibernateBaseTest {
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

    @Before
    public void prepareData() {
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
    public void whenRemoveVehicle_ThenCannotGetCompliantMappingsFromTemporaryOwner() {
        doInTransaction(session -> {
            Vehicle bicycle = session.find(Vehicle.class, BICYCLE);
            session.remove(bicycle);
        });

        doInTransaction(session -> {
            TemporaryOwner jack = session.find(TemporaryOwner.class, "Jack");

            assertEquals(1, jack.getOwnerVehicleMappings().size());
        });
    }

    @Test
    public void whenRemoveVehicle_ThenCannotGetCompliantMappingsWithSelectAllJpa() {
        doInTransaction(session -> {
            Vehicle bicycle = session.find(Vehicle.class, BICYCLE);
            session.remove(bicycle);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllJpql(session, OwnerVehicleMapping.class);

            assertEquals(2, ownerVehicleMappings.size());
        });
    }

    @Test
    public void whenRemoveTemporaryOwner_ThenCannotGetCompliantMappingsFromVehicle() {
        doInTransaction(session -> {
            TemporaryOwner jack = session.find(TemporaryOwner.class, "Jack");
            session.remove(jack);
        });

        doInTransaction(session -> {
            Vehicle bicycle = session.find(Vehicle.class, BICYCLE);

            assertEquals(1, bicycle.getOwnerVehicleMappings().size());
        });
    }

    @Test
    public void whenRemoveAllVehicles_ThenCannotGetAnyMappingWithSelectAllJpa() {
        doInTransaction(session -> {
            List<Vehicle> vehicles = HibernateUtils.selectAllJpql(session, Vehicle.class);
            vehicles.forEach(session::remove);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllJpql(session, OwnerVehicleMapping.class);

            assertTrue(ownerVehicleMappings.isEmpty());
        });
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenRemoveAllVehicles_ThenWhileGettingMappingsWithSelectAllNativeThrowEntityNotFoundException() {
        doInTransaction(session -> {
            List<Vehicle> vehicles = HibernateUtils.selectAllJpql(session, Vehicle.class);
            vehicles.forEach(session::remove);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllNative(session, OwnerVehicleMapping.class);

            assertTrue(ownerVehicleMappings.isEmpty());
        });
    }

    @Test
    public void whenRemoveAllMappings_ThenTemporaryOwnersAndVehiclesAreNotChanged() {
        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllJpql(session, OwnerVehicleMapping.class);
            ownerVehicleMappings.forEach(session::remove);
        });

        doInTransaction(session -> {
            List<TemporaryOwner> owners = HibernateUtils.selectAllJpql(session, TemporaryOwner.class);

            assertEquals(2, owners.size());
        });

        doInTransaction(session -> {
            List<Vehicle> vehicles = HibernateUtils.selectAllJpql(session, Vehicle.class);

            assertEquals(2, vehicles.size());
        });
    }

    @Entity(name = "TemporaryOwner")
    @Table(name = "temporary_owner")
    @SQLDelete(sql = "UPDATE temporary_owner SET deleted = true WHERE id = ?")
    @NamedQuery(name = "findTemporaryOwnerById", query = "select t from TemporaryOwner t where t.id = ?1 and t.deleted = false")
    @Loader(namedQuery = "findTemporaryOwnerById")
    @Where(clause = "deleted = false")
    @Getter
    @Setter
    @NoArgsConstructor
    static class TemporaryOwner {
        @Id
        private String id;

        private boolean deleted;

        @OneToMany(mappedBy = "temporaryOwner", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OwnerVehicleMapping> ownerVehicleMappings = new ArrayList<>();

        TemporaryOwner(String id) {
            this.id = id;
        }
    }

    @Entity(name = "OwnerVehicleMapping")
    @Table(name = "owner_vehicle_mapping")
    @Where(clause = "false = (SELECT v.deleted FROM vehicle v WHERE v.id = vehicle_id)")
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
        @Where(clause = "false = (SELECT v.deleted FROM vehicle v WHERE v.id = vehicle_id)")
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
    @SQLDelete(sql = "UPDATE vehicle SET deleted = true WHERE id = ?")
    @NamedQuery(name = "findVehicleById", query = "select v from Vehicle v where id = ?1 and deleted = false")
    @Loader(namedQuery = "findVehicleById")
    @Where(clause = "deleted = false")
    @Getter
    @Setter
    @NoArgsConstructor
    static class Vehicle {
        @Id
        @Enumerated(EnumType.STRING)
        private Type id;

        private boolean deleted;

        @OneToMany(mappedBy = "vehicle")
//        @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
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
