package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>TestQuestion</code> class represents the TestQuestion entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "group")
public class Group implements Serializable {
    private static final long serialVersionUID = 2784574693156159852L;

    private Integer id;

    private String name;

    @Id
    @SequenceGenerator(name = "GroupIdGenerator", sequenceName = "seq_group", allocationSize = 1)
    @GeneratedValue(generator = "GroupIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 16)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }

        Group group = (Group) o;

        return id.equals(group.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
