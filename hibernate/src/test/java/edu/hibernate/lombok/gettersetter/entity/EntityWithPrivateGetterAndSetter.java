package edu.hibernate.lombok.gettersetter.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "EntityWithPrivateGetterAndSetter")
@Table(name = "entity_with_private_getter_and_setter")
@Data
@NoArgsConstructor
public class EntityWithPrivateGetterAndSetter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String publicData;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String privateData = "private data";
}
