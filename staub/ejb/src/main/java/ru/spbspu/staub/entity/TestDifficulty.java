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
public class TestDifficulty implements Comparable<TestDifficulty>, Serializable {
    private static final long serialVersionUID = -334261114330418617L;

    private TestDifficultyPK id;

    private Test test;

    private Difficulty difficulty;

    private Integer questionsCount;

    /**
     * Constructs a new <code>TestDifficulty</code> instance with the test, difficulty and questions count parameters
     * specified. This constructor also updates a relationship in the Test entity.
     *
     * @param test           the test
     * @param difficulty     the difficulty
     * @param questionsCount the number of questions to select
     */
    public TestDifficulty(Test test, Difficulty difficulty, Integer questionsCount) {
        id = new TestDifficultyPK(test, difficulty);

        this.test = test;
        this.difficulty = difficulty;
        this.questionsCount = questionsCount;

        Set<TestDifficulty> difficultyLevels = id.getTest().getDifficultyLevels();
        if (difficultyLevels == null) {
            difficultyLevels = new HashSet<TestDifficulty>();
            id.getTest().setDifficultyLevels(difficultyLevels);
        }
        difficultyLevels.add(this);
    }

    protected TestDifficulty() {
        id = new TestDifficultyPK();
    }

    @EmbeddedId
    protected TestDifficultyPK getId() {
        return id;
    }

    protected void setId(TestDifficultyPK id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "fk_test", referencedColumnName = "id", insertable = false, updatable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @ManyToOne
    @JoinColumn(name = "fk_difficulty", referencedColumnName = "id", insertable = false, updatable = false)
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Basic
    @Column(name = "questions_count", length = 10)
    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }

    public int compareTo(TestDifficulty other) {
        return id.getDifficulty().getCode().compareTo(other.id.getDifficulty().getCode());
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof TestDifficulty)) {
            return false;
        }

        TestDifficulty other = (TestDifficulty) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TestDifficulty");
        sb.append("{id=").append(id);
        sb.append(", questionsCount=").append(questionsCount);
        sb.append('}');
        return sb.toString();
    }
}
