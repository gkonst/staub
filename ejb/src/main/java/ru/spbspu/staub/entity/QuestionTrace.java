package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * The <code>QuestionTrace</code> class represents QuestionTrace entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "question_trace")
public class QuestionTrace implements Serializable {
    private static final long serialVersionUID = -2332696164283250715L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "QuestionTraceIdGenerator", sequenceName = "seq_question_trace", allocationSize = 1)
    @GeneratedValue(generator = "QuestionTraceIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    private String answer;

    @Basic
    @Lob
    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private Question question;

    @OneToOne
    @JoinColumn(name = "fk_question", referencedColumnName = "id", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    private TestTrace testTrace;

    @ManyToOne
    @JoinColumn(name = "fk_test_trace", referencedColumnName = "id", nullable = false)
    public TestTrace getTestTrace() {
        return testTrace;
    }

    public void setTestTrace(TestTrace testTrace) {
        this.testTrace = testTrace;
    }

    private Boolean correct;

    @Basic
    @Column(name = "correct")
    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof QuestionTrace)) {
            return false;
        }

        QuestionTrace other = (QuestionTrace) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("QuestionTrace");
        sb.append("{id=").append(id);
        sb.append('}');

        return sb.toString();
    }
}
