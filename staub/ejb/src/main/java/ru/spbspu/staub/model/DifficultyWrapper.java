package ru.spbspu.staub.model;

import ru.spbspu.staub.entity.Difficulty;

/**
 * Wrapper for <code>TestDifficulty</code> entity.
 *
 * @author Konstantin Grigoriev
 */
public class DifficultyWrapper {
    private Boolean selected;
    private Difficulty difficulty;
    private Integer questionsCount;
    private Integer passScore;

    public DifficultyWrapper(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.selected = Boolean.FALSE;
        this.questionsCount = 0;
        this.passScore = 100;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }
}
