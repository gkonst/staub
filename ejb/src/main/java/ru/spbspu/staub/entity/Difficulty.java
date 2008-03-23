package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>Difficulty</code> class represents the Difficulty entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "difficulty")
public class Difficulty implements Serializable {
    private static final long serialVersionUID = -8187631088565928741L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "DifficultyIdGenerator", sequenceName = "seq_difficulty", allocationSize = 1)
    @GeneratedValue(generator = "DifficultyIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    @Basic
    @Column(name = "name", length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer code;

    @Basic
    @Column(name = "code", length = 10)
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Difficulty)) {
            return false;
        }

        Difficulty other = (Difficulty) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Difficulty");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", code=").append(code);
        sb.append('}');

        return sb.toString();
    }
}
