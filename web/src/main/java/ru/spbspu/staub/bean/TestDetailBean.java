package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.service.TestService;
import ru.spbspu.staub.service.TestTraceService;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testDetailBean")
@Scope(SESSION)
public class TestDetailBean extends GenericDetailBean<Test> {

    @In
    private TestService testService;

    @In
    private TestTraceService testTraceService;

    @Out(required = false, scope = ScopeType.CONVERSATION)
    private Integer testTraceId;

    private TestTrace testTrace;

    @Override
    protected void fillModel(Integer modelId) {
        setModel(testService.findById(modelId));
    }

    public String prepareTest() {
        testTrace = testTraceService.findTestTrace(getModel().getId(), getSessionId(), getCurrentUser());
        return "testPrepare";
    }

    public String startTest() {
        testTrace = testTraceService.startTest(testTrace);
        this.testTraceId = testTrace.getId();
        return "questionDetail";    
    }
}