package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    public TestDifficulty() {
        // do nothing
    }

    /**
     * Constructs a new <code>TestDifficulty</code> instance with the test, difficulty and questions count parameters
     * specified. This constructor also updates a relationship in the Test entity.
     *
     * @param test           the test
     * @param difficulty     the difficulty
     * @param questionsCount the number of questions to select
     */
    public TestDifficulty(Test test, Difficulty difficulty, Integer questionsCount) {
        this.test = test;
        this.difficulty = difficulty;
        this.questionsCount = questionsCount;

        Set<TestDifficulty> difficultyLevels = this.test.getDifficultyLevels();
        if (difficultyLevels == null) {
            difficultyLevels = new HashSet<TestDifficulty>();
        }
        difficultyLevels.add(this);
    }

    private Test test;

    @Id
    @Column(name = "fk_test", nullable = false, length = 10)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    private Difficulty difficulty;

    @Id
    @Column(name = "fk_difficulty", nullable = false, length = 10)
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
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

    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof TestDifficulty)) {
            return false;
        }

        TestDifficulty other = (TestDifficulty) otherObject;

        return difficulty.equals(other.difficulty) && test.equals(other.test);
    }

    public int hashCode() {
        int result;
        result = test.hashCode();
        result = 31 * result + difficulty.hashCode();
        return result;
    }
}
