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

    private Integer passScore;

    /**
     * Constructs a new <code>TestDifficulty</code> instance with the test, difficulty and questions count parameters
     * specified. This constructor also updates a relationship in the Test entity.
     *
     * @param test           the test
     * @param difficulty     the difficulty
     * @param questionsCount the number of questions to select
     * @param passScore      the minimal score to pass the test's part
     */
    public TestDifficulty(Test test, Difficulty difficulty, Integer questionsCount, Integer passScore) {
        id = new TestDifficultyPK(test.getId(), difficulty.getId());

        this.test = test;
        this.difficulty = difficulty;
        this.questionsCount = questionsCount;
        this.passScore = passScore;

        updateCollection();
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
    @Column(name = "questions_count", length = 10, nullable = false)
    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }

    @Basic
    @Column(name = "pass_score", length = 10, nullable = false)
    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public int compareTo(TestDifficulty other) {
        return difficulty.getCode().compareTo(difficulty.getCode());
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
        sb.append(", test=").append(test);
        sb.append(", difficulty=").append(difficulty);
        sb.append(", questionsCount=").append(questionsCount);
        sb.append(", passScore=").append(passScore);
        sb.append('}');
        return sb.toString();
    }

    private void updateCollection() {
        Set<TestDifficulty> difficultyLevels = test.getDifficultyLevels();
        if (difficultyLevels == null) {
            difficultyLevels = new HashSet<TestDifficulty>();
            test.setDifficultyLevels(difficultyLevels);
        }
        difficultyLevels.add(this);
    }
}
