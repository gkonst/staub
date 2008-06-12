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

    private Integer correctAnswersPercent;

    private Integer n1;

    private Integer n2;

    private Integer n3;

    private Integer n4;

    private Double k1;

    private Double k2;

    private Double k3;

    private Double k4;

    private Date lastUpdate;

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
    @Column(name = "correct_answers_pc", length = 10)
    public Integer getCorrectAnswersPercent() {
        return correctAnswersPercent;
    }

    public void setCorrectAnswersPercent(Integer correctAnswersPercent) {
        this.correctAnswersPercent = correctAnswersPercent;
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
    @Column(name = "k1")
    public Double getK1() {
        return k1;
    }

    public void setK1(Double k1) {
        this.k1 = k1;
    }

    @Basic
    @Column(name = "k2")
    public Double getK2() {
        return k2;
    }

    public void setK2(Double k2) {
        this.k2 = k2;
    }

    @Basic
    @Column(name = "k3")
    public Double getK3() {
        return k3;
    }

    public void setK3(Double k3) {
        this.k3 = k3;
    }

    @Basic
    @Column(name = "k4")
    public Double getK4() {
        return k4;
    }

    public void setK4(Double k4) {
        this.k4 = k4;
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
        sb.append(", correctAnswersPercent=").append(correctAnswersPercent);
        sb.append(", n1=").append(n1);
        sb.append(", n2=").append(n2);
        sb.append(", n3=").append(n3);
        sb.append(", n4=").append(n4);
        sb.append(", k1=").append(k1);
        sb.append(", k2=").append(k2);
        sb.append(", k3=").append(k3);
        sb.append(", k4=").append(k4);
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append('}');

        return sb.toString();
    }
}
