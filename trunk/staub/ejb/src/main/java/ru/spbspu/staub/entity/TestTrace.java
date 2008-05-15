package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    private Date started;

    private Date finished;

    private Test test;

    private Student student;

    private Assignment assignment;

    private Integer score;

    private Boolean testPassed;

    private List<QuestionTrace> questionTraces;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "started")
    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finished")
    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    @ManyToOne
    @JoinColumn(name = "fk_test", referencedColumnName = "id", nullable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @ManyToOne
    @JoinColumn(name = "fk_student", referencedColumnName = "id", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @OneToOne
    @JoinColumn(name = "fk_assignment", referencedColumnName = "id")
    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Basic
    @Column(name = "score", length = 10)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "test_passed")
    public Boolean getTestPassed() {
        return testPassed;
    }

    public void setTestPassed(Boolean testPassed) {
        this.testPassed = testPassed;
    }

    @OneToMany(mappedBy = "testTrace")
    public List<QuestionTrace> getQuestionTraces() {
        return questionTraces;
    }

    public void setQuestionTraces(List<QuestionTrace> questionTraces) {
        this.questionTraces = questionTraces;
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
        sb.append(", started=").append(started);
        sb.append(", finished=").append(finished);
        sb.append(", test=").append(test);
        sb.append(", student=").append(student);
        sb.append(", assignment=").append(assignment);
        sb.append(", score=").append(score);
        sb.append(", testPassed=").append(testPassed);
        sb.append('}');

        return sb.toString();
    }
}
