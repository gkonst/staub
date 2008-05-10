package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

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
    @Column(name = "last_update")
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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
