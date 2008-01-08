package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>Question</code> class represents the Question entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(name = "question", schema = "staub")
public class Question implements Serializable {
    private static final long serialVersionUID = 8070548991033139442L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "QuestionIdGenerator", sequenceName = "seq_question", allocationSize = 1)
    @GeneratedValue(generator = "QuestionIdGenerator", strategy = GenerationType.SEQUENCE)
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

    private String definition;

    @Basic
    @Lob
    @Column(name = "definition", nullable = false)
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    private Test test;

    @ManyToOne
    @JoinColumn(name = "fk_test", referencedColumnName = "id", nullable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Question)) {
            return false;
        }

        Question other = (Question) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", definition='").append(definition).append('\'');
        sb.append(", test=").append(test);
        sb.append('}');

        return sb.toString();
    }
}
