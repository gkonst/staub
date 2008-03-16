package ru.spbspu.staub.bean.test;

import org.jboss.seam.ScopeType;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.service.TestService;
import ru.spbspu.staub.service.TestTraceService;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testStartBean")
@Scope(SESSION)
public class TestStartBean extends GenericDetailBean<Test> {

    @In
    private TestService testService;

    @In
    private TestTraceService testTraceService;

    @In
    private User user;

    @Out(required = false, scope = ScopeType.CONVERSATION)
    private TestTrace testTrace;

    @Override
    protected void fillModel(Integer modelId) {
        setModel(testService.findById(modelId));
    }

    public String prepareTest() {
        testTrace = testTraceService.findTestTrace(getModel(), getSessionId(), user);
        testTrace = testTraceService.startTest(testTrace); // may be moved to startTest()
        return "testPrepare";
    }

    // temporarily unused
    public String startTest() {
        testTrace = testTraceService.startTest(testTrace);
        return "questionDetail";
    }
}
