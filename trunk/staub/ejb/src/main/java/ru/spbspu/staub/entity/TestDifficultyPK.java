package ru.spbspu.staub.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * The <code>TestDifficultyPK</code> class represents a primary key of the {@link TestDifficulty} entity.
 *
 * @author Alexander V. Elagin
 */
@SuppressWarnings({"JpaModelErrorInspection"}) // Make IDE happy :)
@Embeddable
public class TestDifficultyPK implements Serializable {
    private static final long serialVersionUID = 6673970679487117717L;

    private Test test;

    private Difficulty difficulty;

    public TestDifficultyPK() {
        // do nothing
    }

    public TestDifficultyPK(Test test, Difficulty difficulty) {
        this.test = test;
        this.difficulty = difficulty;
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
    @JoinColumn(name = "fk_difficulty", referencedColumnName = "id", nullable = false)
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof TestDifficultyPK)) {
            return false;
        }

        TestDifficultyPK other = (TestDifficultyPK) otherObject;

        return difficulty.equals(other.difficulty);
    }

    @Override
    public int hashCode() {
        return difficulty.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TestDifficultyPK");
        sb.append("{test=").append(test);
        sb.append(", difficulty=").append(difficulty);
        sb.append('}');
        return sb.toString();
    }
}
