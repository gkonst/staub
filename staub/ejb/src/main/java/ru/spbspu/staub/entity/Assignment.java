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

    private Integer fkTest;

    @Basic
    @Column(name = "fk_test")
    public Integer getFkTest() {
        return fkTest;
    }

    public void setFkTest(Integer fkTest) {
        this.fkTest = fkTest;
    }

    private Integer fkUser;

    @Basic
    @Column(name = "fk_user")
    public Integer getFkUser() {
        return fkUser;
    }

    public void setFkUser(Integer fkUser) {
        this.fkUser = fkUser;
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
        sb.append(", fkUser=").append(fkUser);
        sb.append(", fkTest=").append(fkTest);
        sb.append('}');

        return sb.toString();
    }
}
