package ru.spbspu.staub.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Entity
@Table(name = "test")
public class Test {

    private Long id;

    private String name;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
