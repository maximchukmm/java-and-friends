package edu.hibernate.lombok.gettersetter.entity;

import lombok.*;

import javax.persistence.*;

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
