package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The <code>Assignment</code> class represents the Assignment entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "assignment")
public class Assignment implements Serializable {
    private static final long serialVersionUID = -8433327776647292591L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "AssignmentIdGenerator", sequenceName = "seq_assignment", allocationSize = 1)
    @GeneratedValue(generator = "AssignmentIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    private Student student;

    @OneToOne
    @JoinColumn(name = "fk_student", referencedColumnName = "id", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    private Test test;

    @OneToOne
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
        if (!(otherObject instanceof Assignment)) {
            return false;
        }

        Assignment other = (Assignment) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Assignment");
        sb.append("{id=").append(id);
        sb.append(", fkStudent=").append(student);
        sb.append(", fkTest=").append(test);
        sb.append('}');

        return sb.toString();
    }
}
