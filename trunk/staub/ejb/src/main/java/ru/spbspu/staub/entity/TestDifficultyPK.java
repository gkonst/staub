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

    private Integer fkTest;

    @Column(name = "fk_test", nullable = false, length = 10)
    @Id
    public Integer getFkTest() {
        return fkTest;
    }

    public void setFkTest(Integer fkTest) {
        this.fkTest = fkTest;
    }

    private Integer fkDifficulty;

    @Column(name = "fk_difficulty", nullable = false, length = 10)
    @Id
    public Integer getFkDifficulty() {
        return fkDifficulty;
    }

    public void setFkDifficulty(Integer fkDifficulty) {
        this.fkDifficulty = fkDifficulty;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestDifficultyPK that = (TestDifficultyPK) o;

        return (fkDifficulty != null ? fkDifficulty.equals(that.fkDifficulty) : that.fkDifficulty == null)
                && (fkTest != null ? fkTest.equals(that.fkTest) : that.fkTest == null);

    }

    public int hashCode() {
        int result;
        result = (fkTest != null ? fkTest.hashCode() : 0);
        result = 31 * result + (fkDifficulty != null ? fkDifficulty.hashCode() : 0);
        return result;
    }
}
