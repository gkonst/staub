package ru.spbspu.staub.util;

import static org.jboss.seam.ScopeType.APPLICATION;
import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import ru.spbspu.staub.timer.ExpiredObjectsProcessor;

/**
 * Seam Component used for initializing Staub Application.
 *
 * @author Konstantin Grigoriev
 */
@Scope(APPLICATION)
@Name("ru.spbspu.staub.initializer")
@Startup
public class StaubInitializer {

    private Log logger = Logging.getLog(StaubInitializer.class);

    @In
    private ExpiredObjectsProcessor expiredObjectsProcessor;

    @Create
    public void init() {
        logger.debug("Initializing Staub Application...");
        expiredObjectsProcessor.startTimer();
        logger.debug("Initializing Staub Application...Ok");
    }

    @Destroy
    public void destroy() {
        logger.debug("Destroing Staub Application...");
        expiredObjectsProcessor.stopTimer();
        logger.debug("Destroing Staub Application...Ok");
    }
}
