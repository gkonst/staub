package ru.spbspu.staub.util;

import java.util.Date;

/**
 * Timer represantation.
 *
 * @author Konstantin Grigoriev
 */
public class TimerModel {
    private Date startTime;
    private int timeLimit;

    public TimerModel(Date startTime, int timeLimit) {
        this.startTime = startTime;
        this.timeLimit = timeLimit;
    }

    public int getTimeToCount() {
        return timeLimit - getTimeElapsed();
    }

    public boolean isExpired() {
        return getTimeElapsed() >= timeLimit;
    }

    private int getTimeElapsed() {
        return (int)((new Date().getTime() - startTime.getTime()) / 1000);    
    }
}
