package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The <code>QuestionStatistics</code> class represents the QuestionStatistics entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "question_statistics")
public class QuestionStatistics implements Serializable {
    private static final long serialVersionUID = 8883462756236885172L;

    private Integer fkQuestion;

    private Question question;

    private Integer totalAnswers;

    private Integer correctAnswers;

    private Integer n1;

    private Integer n2;

    private Integer n3;

    private Integer n4;

    private Date lastUpdate;

    /**
     * Constructs a new <code>QuestionStatistics</code> entity.
     *
     * @param question       the question
     * @param totalAnswers   the total answers count
     * @param correctAnswers the correct answers count
     */
    public QuestionStatistics(Question question, Integer totalAnswers, Integer correctAnswers) {
        fkQuestion = question.getId();
        this.question = question;
        this.totalAnswers = totalAnswers;
        this.correctAnswers = correctAnswers;
        setLastUpdate(new Date());
    }

    protected QuestionStatistics() {
        // do nothing
    }

    @Id
    @Column(name = "fk_question", nullable = false, length = 10)
    public Integer getFkQuestion() {
        return fkQuestion;
    }

    public void setFkQuestion(Integer fkQuestion) {
        this.fkQuestion = fkQuestion;
    }

    @OneToOne
    @JoinColumn(name = "fk_question", referencedColumnName = "id", insertable = false, updatable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Basic
    @Column(name = "total_answers", length = 10)
    public Integer getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(Integer totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    @Basic
    @Column(name = "correct_answers", length = 10)
    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    @Basic
    @Column(name = "n1", length = 10)
    public Integer getN1() {
        return n1;
    }

    public void setN1(Integer n1) {
        this.n1 = n1;
    }

    @Basic
    @Column(name = "n2", length = 10)
    public Integer getN2() {
        return n2;
    }

    public void setN2(Integer n2) {
        this.n2 = n2;
    }

    @Basic
    @Column(name = "n3", length = 10)
    public Integer getN3() {
        return n3;
    }

    public void setN3(Integer n3) {
        this.n3 = n3;
    }

    @Basic
    @Column(name = "n4", length = 10)
    public Integer getN4() {
        return n4;
    }

    public void setN4(Integer n4) {
        this.n4 = n4;
    }

    @Basic
    @Column(name = "last_update")
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Transient
    public int getCorrectAnswersPercent() {
        return (int) ((double) correctAnswers / totalAnswers * 100);
    }

    @Transient
    public double getK1() {
        return (((double) n1 + n3) / totalAnswers);
    }

    @Transient
    public double getK2() {
        return ((double) n2 / (n2 + n4));
    }

    @Transient
    public double getK3() {
        return ((double) n3 / (n1 + n3));
    }

    @Transient
    public double getK4() {
        return (((double) n1 + n4) / totalAnswers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionStatistics)) {
            return false;
        }

        QuestionStatistics that = (QuestionStatistics) o;

        return fkQuestion.equals(that.fkQuestion);

    }

    @Override
    public int hashCode() {
        return fkQuestion.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("QuestionStatistics");
        sb.append("{question=").append(question);
        sb.append(", totalAnswers=").append(totalAnswers);
        sb.append(", correctAnswers=").append(correctAnswers);
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append('}');

        return sb.toString();
    }
}
