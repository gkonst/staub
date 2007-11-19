package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The <code>Test</code> class represents the Test entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(name = "test", schema = "staub")
public class Test implements Serializable {
    private static final long serialVersionUID = -4826754008513352835L;
    
    private Integer id;

    @Id
    @SequenceGenerator(name = "TestIdGenerator", sequenceName = "seq_test", allocationSize = 1)
    @GeneratedValue(generator = "TestIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    @Basic
    @Column(name = "name", length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;

    @Lob
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private List<Question> questions;

    @OneToMany(mappedBy = "test")
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Test)) {
            return false;
        }

        Test other = (Test) otherObject;

        return id.equals(other.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
