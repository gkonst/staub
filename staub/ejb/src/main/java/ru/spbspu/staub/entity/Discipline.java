package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * The <code>Discipline</code> class represents the Discipline entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "discipline")
public class Discipline implements Serializable {
    private static final long serialVersionUID = 1205373104634109774L;

    private Integer id;

    private String name;

    private String code;

    private Set<Category> categories;

    @Id
    @SequenceGenerator(name = "DisciplineIdGenerator", sequenceName = "seq_discipline", allocationSize = 1)
    @GeneratedValue(generator = "DisciplineIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code", length = 8)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.REMOVE)
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Discipline)) {
            return false;
        }

        Discipline other = (Discipline) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Discipline");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
