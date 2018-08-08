package edu.hibernate.lombok.gettersetter.entity;

import javax.persistence.*;

@Entity(name = "EntityWithoutGetterAndSetter")
@Table(name = "entity_without_getter_and_setter")
public class EntityWithoutGetterAndSetter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String publicData;

    private String privateData = "private data";

    public EntityWithoutGetterAndSetter() {

    }

    public EntityWithoutGetterAndSetter(String publicData) {
        this.publicData = publicData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicData() {
        return publicData;
    }

    public void setPublicData(String publicData) {
        this.publicData = publicData;
    }
}
