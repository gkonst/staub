package ru.spbspu.staub.interceptors;

import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * The <code>CallLogger</code> class is an interceptor class implements common call logging code.
 *
 * @author Alexander V. Elagin
 */
public class CallLogger {
    private static final Log LOG = Logging.getLog(CallLogger.class);

    @AroundInvoke
    public Object logMethodCall(InvocationContext invocationContext) throws Exception {
        String methodName = invocationContext.getMethod().getName();
        LOG.debug(" Method #0 started...", methodName);
        int i = 1;
        for (Object parameter : invocationContext.getParameters()) {
            LOG.debug("  Parameter #0: #1", i, parameter);
            i++;
        }
        try {
            return invocationContext.proceed();
        } catch (Exception e) {
            LOG.debug(" Method #0 throwed an exception: #1", methodName, e.getMessage());
            throw e;
        } finally {
            LOG.debug(" Method #0 finished.", methodName);
        }
    }
}
