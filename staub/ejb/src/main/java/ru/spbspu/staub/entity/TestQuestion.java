package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>TestQuestion</code> class represents the TestQuestion entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "test_question")
@IdClass(ru.spbspu.staub.entity.TestQuestionPK.class)
public class TestQuestion implements Serializable {
    private static final long serialVersionUID = 94759922294567430L;

    private Integer fkTest;

    @Id
    @Column(name = "fk_test", nullable = false, length = 10)
    public Integer getFkTest() {
        return fkTest;
    }

    public void setFkTest(Integer fkTest) {
        this.fkTest = fkTest;
    }

    private Integer fkQuestion;

    @Id
    @Column(name = "fk_question", nullable = false, length = 10)
    public Integer getFkQuestion() {
        return fkQuestion;
    }

    public void setFkQuestion(Integer fkQuestion) {
        this.fkQuestion = fkQuestion;
    }
}
