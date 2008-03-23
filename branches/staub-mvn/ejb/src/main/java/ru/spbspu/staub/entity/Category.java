package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>Category</code> class represents the Category entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = 2761822203636106066L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "CategoryIdGenerator", sequenceName = "seq_category", allocationSize = 1)
    @GeneratedValue(generator = "CategoryIdGenerator", strategy = GenerationType.SEQUENCE)
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
        if (!(otherObject instanceof Category)) {
            return false;
        }

        Category other = (Category) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", code=").append(code);
        sb.append('}');

        return sb.toString();
    }
}
