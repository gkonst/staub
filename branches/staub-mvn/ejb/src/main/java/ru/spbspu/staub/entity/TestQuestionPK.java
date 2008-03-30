package ru.spbspu.staub.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The <code>TestQuestionPK</code> class represents a primary key of the {@link TestQuestion} entity.
 *
 * @author Alexander V. Elagin
 */
public class TestQuestionPK implements Serializable {
    private static final long serialVersionUID = -1574448173473250520L;

    private Integer fkTest;

    @Column(name = "fk_test", nullable = false, length = 10)
    @Id
    public Integer getFkTest() {
        return fkTest;
    }

    public void setFkTest(Integer fkTest) {
        this.fkTest = fkTest;
    }

    private Integer fkQuestion;

    @Column(name = "fk_question", nullable = false, length = 10)
    @Id
    public Integer getFkQuestion() {
        return fkQuestion;
    }

    public void setFkQuestion(Integer fkQuestion) {
        this.fkQuestion = fkQuestion;
    }

    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if ((otherObject == null) || (getClass() != otherObject.getClass())) {
            return false;
        }

        TestQuestionPK other = (TestQuestionPK) otherObject;

        return ((fkQuestion != null) ? fkQuestion.equals(other.fkQuestion) : (other.fkQuestion == null))
                && ((fkTest != null) ? fkTest.equals(other.fkTest) : (other.fkTest != null));

    }

    public int hashCode() {
        int result;
        result = (fkTest != null ? fkTest.hashCode() : 0);
        result = 31 * result + (fkQuestion != null ? fkQuestion.hashCode() : 0);
        return result;
    }
}
