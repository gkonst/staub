package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The <code>UserHistory</code> class represents the UserHistory entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "user_history")
public class UserHistory implements Serializable {
    private static final long serialVersionUID = -5179643109315329100L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "UserHistoryIdGenerator", sequenceName = "seq_user_history", allocationSize = 1)
    @GeneratedValue(generator = "UserHistoryIdGenerator", strategy = GenerationType.SEQUENCE)
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

    private Integer score;

    @Basic
    @Column(name = "score", length = 10)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    private Integer testPassed;

    @Basic
    @Column(name = "test_passed", length = 1)
    public Integer getTestPassed() {
        return testPassed;
    }

    public void setTestPassed(Integer testPassed) {
        this.testPassed = testPassed;
    }

    private Date testDate;

    @Basic
    @Column(name = "test_date", length = 13)
    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    private TestTrace testTrace;

    @OneToOne
    @JoinColumn(name = "fk_test_trace", referencedColumnName = "id")
    public TestTrace getTestTrace() {
        return testTrace;
    }

    public void setTestTrace(TestTrace testTrace) {
        this.testTrace = testTrace;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof UserHistory)) {
            return false;
        }

        UserHistory other = (UserHistory) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserHistory");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", score=").append(score);
        sb.append(", testPassed=").append(testPassed);
        sb.append(", testDate='").append(testDate).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
