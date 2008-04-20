package ru.spbspu.staub.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The <code>TestDifficultyPK</code> class represents a primary key of the {@link TestDifficulty} entity.
 *
 * @author Alexander V. Elagin
 */
public class TestDifficultyPK implements Serializable {
    private static final long serialVersionUID = 6673970679487117717L;

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

    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof TestDifficultyPK)) {
            return false;
        }

        TestDifficultyPK other = (TestDifficultyPK) otherObject;

        return difficulty.equals(other.difficulty) && test.equals(other.test);
    }

    public int hashCode() {
        int result;
        result = test.hashCode();
        result = 31 * result + difficulty.hashCode();
        return result;
    }
}
