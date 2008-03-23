package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * The <code>Assignment</code> class represents the Assignment entity.
 *
 *  @author Alexander V. Elagin
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

    private Date testBegin;

    @Basic
    @Column(name = "test_begin", length = 13)
    public Date getTestBegin() {
        return testBegin;
    }

    public void setTestBegin(Date testBegin) {
        this.testBegin = testBegin;
    }

    private Date testEnd;

    @Basic
    @Column(name = "test_end", length = 13)
    public Date getTestEnd() {
        return testEnd;
    }

    public void setTestEnd(Date testEnd) {
        this.testEnd = testEnd;
    }

    private Boolean testStarted;

    @Basic
    @Column(name = "test_started", length = 1)
    public Boolean getTestStarted() {
        return testStarted;
    }

    public void setTestStarted(Boolean testStarted) {
        this.testStarted = testStarted;
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

    private User user;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        sb.append(", testBegin=").append(testBegin);
        sb.append(", testEnd=").append(testEnd);
        sb.append(", testStarted=").append(testStarted);
        sb.append(", user=").append(user);
        sb.append(", test=").append(test);
        sb.append('}');

        return sb.toString();
    }
}
