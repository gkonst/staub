package ru.spbspu.staub.statistics;

import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Test;

import java.io.Serializable;

/**
 * The <code>GroupStatistics</code> class aggregates statistical data.
 *
 * @author Alexander V. Elagin
 */
public class GroupStatistics implements Serializable {
    private static final long serialVersionUID = -7758041456771135968L;

    private Group group;

    private Test test;

    private int totalAnswers;

    private int correctAnswers;

    public GroupStatistics() {
        // do nothing
    }

    public GroupStatistics(Group group, Test test, int totalAnswers, int correctAnswers) {
        this.group = group;
        this.test = test;
        this.totalAnswers = totalAnswers;
        this.correctAnswers = correctAnswers;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getCorrectAnswersPercent() {
        return correctAnswers * totalAnswers / 100;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GroupStatistics");
        sb.append("{group=").append(group);
        sb.append(", test=").append(test);
        sb.append(", totalAnswers=").append(totalAnswers);
        sb.append(", correctAnswers=").append(correctAnswers);
        sb.append('}');

        return sb.toString();
    }
}
