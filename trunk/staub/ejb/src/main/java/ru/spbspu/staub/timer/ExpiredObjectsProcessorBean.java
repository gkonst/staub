package ru.spbspu.staub.timer;

import ru.spbspu.staub.service.AssignmentService;
import ru.spbspu.staub.service.TestTraceService;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Collection;

/**
 * The <code>ExpiredObjectsProcessorBean</code> is a stateless EJB for expired objects processing according to timer
 * events.
 *
 * @author Alexander V. Elagin
 */
@Stateless
public class ExpiredObjectsProcessorBean implements ExpiredObjectsProcessor {
    private static final long INTERVAL = 10 * 60 * 1000; // 10 minutes

    @Resource
    private SessionContext sessionContext;

    @EJB
    private AssignmentService assignmentService;

    @EJB
    private TestTraceService testTraceService;

    public void startTimer() {
        TimerService timerService = sessionContext.getTimerService();
        timerService.createTimer(0, INTERVAL, null);
    }

    @SuppressWarnings("unchecked")
    public void stopTimer() {
        TimerService timerService = sessionContext.getTimerService();
        for (Timer timer : (Collection<Timer>) timerService.getTimers()) {
            timer.cancel();
        }
    }

    @Timeout
    public void process(Timer timer) {
        assignmentService.processExpiredAssignments();
        testTraceService.processExpiredTestTraces();
    }
}
