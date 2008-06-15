package ru.spbspu.staub.timer;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
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
@Name("expiredObjectsProcessor")
@AutoCreate
@Stateless
public class ExpiredObjectsProcessorBean implements ExpiredObjectsProcessor {
    private static final long INTERVAL = 10 * 60 * 1000; // 10 minutes

    @Resource
    private SessionContext sessionContext;

    @EJB
    private AssignmentService assignmentService;

    @EJB
    private TestTraceService testTraceService;

    @Logger
    protected Log log;

    public void startTimer() {
        log.debug("> startTimer()");

        TimerService timerService = sessionContext.getTimerService();
        timerService.createTimer(0, INTERVAL, null);

        log.debug("< startTimer()");
    }

    @SuppressWarnings("unchecked")
    public void stopTimer() {
        log.debug("> stopTimer()");

        TimerService timerService = sessionContext.getTimerService();
        for (Timer timer : (Collection<Timer>) timerService.getTimers()) {
            timer.cancel();
        }

        log.debug("< stopTimer()");
    }

    @Timeout
    public void process(Timer timer) {
        log.debug("> process(Timer=#0)", timer);

        assignmentService.processExpiredAssignments();
        testTraceService.processExpiredTestTraces();

        log.debug("< process(Timer)");
    }
}
