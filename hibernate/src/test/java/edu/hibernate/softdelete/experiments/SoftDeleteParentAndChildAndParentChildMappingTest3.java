package edu.hibernate.softdelete.experiments;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static edu.hibernate.softdelete.experiments.SoftDeleteParentAndChildAndParentChildMappingTest3.Vehicle.Type.BICYCLE;
import static edu.hibernate.softdelete.experiments.SoftDeleteParentAndChildAndParentChildMappingTest3.Vehicle.Type.UNICYCLE;
import static java.time.Month.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//todo: renaming test cases
public class SoftDeleteParentAndChildAndParentChildMappingTest3 extends HibernateBaseTest {
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

    //когда удаляется vehicle, тогда не могу через temporaryOwner достать mappings удаленного vehicle
    @Test
    public void when1() {
        doInTransaction(session -> {
            Vehicle bicycle = session.find(Vehicle.class, BICYCLE);
            session.remove(bicycle);
        });

        doInTransaction(session -> {
            TemporaryOwner jack = session.find(TemporaryOwner.class, "Jack");

            assertEquals(1, jack.getOwnerVehicleMappings().size());
        });
    }

    //когда удаляется temporary owner, тогда не могу через vehicle достать mappings удаленного temporary owner
    @Test
    public void when2() {
        doInTransaction(session -> {
            TemporaryOwner jack = session.find(TemporaryOwner.class, "Jack");
            session.remove(jack);
        });

        doInTransaction(session -> {
            Vehicle bicycle = session.find(Vehicle.class, BICYCLE);

            assertEquals(1, bicycle.getOwnerVehicleMappings().size());
        });
    }

    //когда удаляются все vehicles, тогда через jpa select не достать ни одного mapping
    @Test
    public void when3() {
        doInTransaction(session -> {
            List<Vehicle> vehicles = HibernateUtils.selectAllJpql(session, Vehicle.class);
            vehicles.forEach(session::remove);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllJpql(session, OwnerVehicleMapping.class);

            assertTrue(ownerVehicleMappings.isEmpty());
        });
    }

    //когда удаляются все vehicles, тогда, доставая через native select все mappings, получу EntityNotFoundException
    @Test(expected = EntityNotFoundException.class)
    public void when4() {
        doInTransaction(session -> {
            List<Vehicle> vehicles = HibernateUtils.selectAllJpql(session, Vehicle.class);
            vehicles.forEach(session::remove);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllNative(session, OwnerVehicleMapping.class);

            assertTrue(ownerVehicleMappings.isEmpty());
        });
    }

    //когда удаляются все temporary owners, тогда, доставая через native select все mappings, получу EntityNotFoundException
    @Test(expected = EntityNotFoundException.class)
    public void when5() {
        doInTransaction(session -> {
            List<TemporaryOwner> temporaryOwners = HibernateUtils.selectAllJpql(session, TemporaryOwner.class);
            temporaryOwners.forEach(session::remove);
        });

        doInTransaction(session -> {
            List<OwnerVehicleMapping> ownerVehicleMappings = HibernateUtils.selectAllNative(session, OwnerVehicleMapping.class);

            assertTrue(ownerVehicleMappings.isEmpty());
        });
    }

    //когда удаляются все mappings, тогда все temporary owners и vehicles остаются на месте
    @Test
    public void when6() {
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

    //когда удаляется один vehicle, тогда по temporary owner находятся только mappings с неудаленными vehicles
    @Test
    public void when7() {
        doInTransaction(session -> {
            Vehicle vehicle = session.find(Vehicle.class, BICYCLE);
            session.remove(vehicle);
        });

        doInTransaction(session -> {
            TemporaryOwner jack = session.find(TemporaryOwner.class, "Jack");

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OwnerVehicleMapping> query = builder.createQuery(OwnerVehicleMapping.class);
            Root<OwnerVehicleMapping> root = query.from(OwnerVehicleMapping.class);
            query
                .select(root)
                .where(
                    builder.equal(root.get("temporaryOwner"), jack)
                );

            List<OwnerVehicleMapping> mappings = session.createQuery(query).getResultList();

            assertEquals(1, mappings.size());
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

        @OneToMany(mappedBy = "temporaryOwner")
        private List<OwnerVehicleMapping> ownerVehicleMappings = new ArrayList<>();

        TemporaryOwner(String id) {
            this.id = id;
        }
    }

    @Entity(name = "OwnerVehicleMapping")
    @Table(name = "owner_vehicle_mapping")
    @Where(clause =
        "false = (SELECT t.deleted FROM temporary_owner t WHERE t.id = temporary_owner_id) " +
            "and " +
            "false = (SELECT v.deleted FROM vehicle v WHERE v.id = vehicle_id)")
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
//        @Where(clause = "false = (SELECT t.deleted FROM temporary_owner t WHERE t.id = temporary_owner_id)")
        private TemporaryOwner temporaryOwner;

        @ManyToOne(optional = false)
        @JoinColumn(name = "vehicle_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_vehicle_id"))
//        @Where(clause = "false = (SELECT v.deleted FROM vehicle v WHERE v.id = vehicle_id)")
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
        private List<OwnerVehicleMapping> ownerVehicleMappings = new ArrayList<>();

        Vehicle(Type id) {
            this.id = id;
        }

        enum Type {
            BICYCLE, UNICYCLE
        }
    }
}
