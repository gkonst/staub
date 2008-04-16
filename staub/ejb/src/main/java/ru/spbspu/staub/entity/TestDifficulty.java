package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>TestDifficulty</code> class represents the TestDifficulty entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "test_difficulty")
@IdClass(ru.spbspu.staub.entity.TestDifficultyPK.class)
public class TestDifficulty implements Serializable {
    private static final long serialVersionUID = -334261114330418617L;
    
    private Integer fkTest;

    @Id
    @Column(name = "fk_test", nullable = false, length = 10)
    public Integer getFkTest() {
        return fkTest;
    }

    public void setFkTest(Integer fkTest) {
        this.fkTest = fkTest;
    }

    private Integer fkDifficulty;

    @Id
    @Column(name = "fk_difficulty", nullable = false, length = 10)
    public Integer getFkDifficulty() {
        return fkDifficulty;
    }

    public void setFkDifficulty(Integer fkDifficulty) {
        this.fkDifficulty = fkDifficulty;
    }

    private Integer questionsCount;

    @Basic
    @Column(name = "questions_count", length = 10)
    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }
}
