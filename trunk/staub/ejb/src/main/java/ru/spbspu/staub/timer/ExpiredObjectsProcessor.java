package ru.spbspu.staub.timer;

import javax.ejb.Local;

/**
 * The <code>ExpiredObjectsProcessor</code> interface is a local interface for the
 * <code>ExpiredObjectsProcessorBean</code> bean.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface ExpiredObjectsProcessor {
    /**
     * Schedules the timer.
     */
    void startTimer();

    /**
     * Deregisters the timer.
     */
    void stopTimer();
}
