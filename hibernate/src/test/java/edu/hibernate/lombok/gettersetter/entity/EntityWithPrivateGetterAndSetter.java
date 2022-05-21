package edu.hibernate.lombok.gettersetter.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
