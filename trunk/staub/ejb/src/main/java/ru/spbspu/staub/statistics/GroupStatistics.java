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

    private long totalAnswers;

    private long correctAnswers;

    public GroupStatistics() {
        // do nothing
    }

    public GroupStatistics(Group group, Test test, long totalAnswers, long correctAnswers) {
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

    public long getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(long totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public long getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(long correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public long getCorrectAnswersPercent() {
        return correctAnswers * 100 / totalAnswers;
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
