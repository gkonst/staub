package ru.spbspu.staub.interceptors;

import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * The <code>CallLogger</code> class is an interceptor class that implements common call logging code.
 *
 * @author Alexander V. Elagin
 */
public class CallLogger {
    private static final Log LOG = Logging.getLog(CallLogger.class);

    @AroundInvoke
    public Object logMethodCall(InvocationContext invocationContext) throws Exception {
        String methodName = invocationContext.getMethod().getName();
        LOG.debug("* Method #0 started...", methodName);
        Object[] parameters = invocationContext.getParameters();
        if (parameters != null) {
            int i = 1;
            for (Object parameter : parameters) {
                LOG.debug("*  Parameter #0: #1", i, parameter);
                i++;
            }
        }
        long startTime = System.currentTimeMillis();
        try {
            Object result = invocationContext.proceed();
            LOG.debug("*  Method #0 returned: #1", methodName, result);
            return result;
        } catch (Exception e) {
            LOG.debug("*  Method #0 throwed an exception: #1", methodName, e.getMessage());
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            LOG.debug("* Method #0 finished. Execution time: #1 ms", methodName, executionTime);
        }
    }
}
