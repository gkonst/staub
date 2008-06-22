package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

    private String name;

    private String code;

    private Discipline discipline;

    private Set<Topic> topics;

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

    @Basic
    @Column(name = "name", length = 256, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code", length = 16, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne
    @JoinColumn(name = "fk_discipline", referencedColumnName = "id", nullable = false)
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
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
        sb.append(", code='").append(code).append('\'');
        sb.append(", discipline=").append(discipline);
        sb.append('}');

        return sb.toString();
    }
}
