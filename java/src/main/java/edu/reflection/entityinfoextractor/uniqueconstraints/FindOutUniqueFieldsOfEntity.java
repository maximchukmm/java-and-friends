package edu.reflection.entityinfoextractor.uniqueconstraints;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static edu.reflection.entityinfoextractor.ClassInfoExtractor.extractUniqueColumnNames;

public class FindOutUniqueFieldsOfEntity {

    public static void main(String[] args) {
        System.out.println("EntityWithUniqueFields:");
        System.out.println(extractUniqueColumnNames(new EntityWithUniqueFields()));

        System.out.println();

        System.out.println("EntityWithUniqueFieldsInTableConstraint:");
        System.out.println(extractUniqueColumnNames(new EntityWithUniqueFieldsInTableConstraint()));

        System.out.println();

        System.out.println("EntityWithSingleUniqueField:");
        System.out.println(extractUniqueColumnNames(new EntityWithSingleUniqueField()));

        System.out.println();

        System.out.println("EntityWithoutUniqueFields:");
        System.out.println(extractUniqueColumnNames(new EntityWithoutUniqueFields()));
    }


}

class EntityWithoutUniqueFields extends BaseEntity {
    @Basic
    @Column(name = "nonUniqueField")
    private String nonUniqueField;
}

class EntityWithSingleUniqueField extends BaseEntity {
    @Basic(optional = false)
    @Column(name = "uniqueField", unique = true)
    private String uniqueField;
}

class EntityWithUniqueFields extends BaseEntity {
    @Basic(optional = false)
    @Column(name = "firstUniqueField", unique = true)
    private String firstUniqueField;

    @Basic(optional = false)
    @Column(name = "secondUniqueField", unique = true)
    private String secondUniqueField;

    @Basic(optional = false)
    @Column(name = "firstNonUniqueField")
    private String firstNonUniqueField;

    @Basic(optional = false)
    @Column(name = "secondNonUniqueField")
    private String secondNonUniqueField;
}


@Entity
@Table(
    name = "production_calendar",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"year", "month", "office_id"})
    }
)
class EntityWithUniqueFieldsInTableConstraint extends BaseEntity {
    private String year;
    private String month;
    private String officeId;
}

