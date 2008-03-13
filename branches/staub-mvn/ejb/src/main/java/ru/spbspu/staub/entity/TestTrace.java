package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * The <code>TestTrace</code> class represents TestTrace entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "test_trace")
public class TestTrace implements Serializable {
    private static final long serialVersionUID = 410759301262862769L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "TestTraceIdGenerator", sequenceName = "seq_test_trace", allocationSize = 1)
    @GeneratedValue(generator = "TestTraceIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String sessionId;

    @Basic
    @Column(name = "session_id", length = 64)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private Date started;

    @Basic
    @Column(name = "started")
    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    private Date finished;

    @Basic
    @Column(name = "finished")
    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
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
        if (!(otherObject instanceof TestTrace)) {
            return false;
        }

        TestTrace other = (TestTrace) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TestTrace");
        sb.append("{id=").append(id);
        sb.append('}');

        return sb.toString();
    }
}
