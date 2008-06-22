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

    private Student student;

    private Test test;

    private TestTrace testTrace;

    private Date testBegin;

    private Date testEnd;

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

    @OneToOne
    @JoinColumn(name = "fk_student", referencedColumnName = "id", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @OneToOne
    @JoinColumn(name = "fk_test", referencedColumnName = "id", nullable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @OneToOne(mappedBy = "assignment")
    public TestTrace getTestTrace() {
        return testTrace;
    }

    public void setTestTrace(TestTrace testTrace) {
        this.testTrace = testTrace;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "test_begin")
    public Date getTestBegin() {
        return testBegin;
    }

    public void setTestBegin(Date testBegin) {
        this.testBegin = testBegin;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "test_end")
    public Date getTestEnd() {
        return testEnd;
    }

    public void setTestEnd(Date testEnd) {
        this.testEnd = testEnd;
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
        sb.append(", student=").append(student);
        sb.append(", test=").append(test);
        sb.append(", testBegin=").append(testBegin);
        sb.append(", testEnd=").append(testEnd);
        sb.append('}');

        return sb.toString();
    }
}
