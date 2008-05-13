package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>Topic</code> class represents the Topic entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "topic")
public class Topic implements Serializable {
    private static final long serialVersionUID = 4023851716967865845L;

    private Integer id;

    private String name;

    private String code;

    private Category category;

    @Id
    @SequenceGenerator(name = "TopicIdGenerator", sequenceName = "seq_topic", allocationSize = 1)
    @GeneratedValue(generator = "TopicIdGenerator", strategy = GenerationType.SEQUENCE)
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
    @Column(name = "code", length = 10, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne
    @JoinColumn(name = "fk_category", referencedColumnName = "id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Topic)) {
            return false;
        }

        Topic other = (Topic) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Topic");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", category=").append(category);
        sb.append('}');

        return sb.toString();
    }
}
