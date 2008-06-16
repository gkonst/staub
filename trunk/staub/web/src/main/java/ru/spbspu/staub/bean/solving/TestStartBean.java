package ru.spbspu.staub.bean.solving;

import org.jboss.seam.ScopeType;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.service.AssignmentService;
import ru.spbspu.staub.service.TestTraceService;

/**
 * Webbean for showing detail data of <code>Assignment</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testStartBean")
@Scope(SESSION)
public class TestStartBean extends GenericDetailBean<Assignment> {
    private static final long serialVersionUID = 3880691395764425507L;

    @In
    private AssignmentService assignmentService;

    @In
    private TestTraceService testTraceService;

    @Out(required = false, scope = ScopeType.CONVERSATION)
    private TestTrace testTrace;

    @Override
    protected void fillModel(Integer modelId) {
        setModel(assignmentService.findById(modelId));
    }

    public String prepareTest() {
        testTrace = testTraceService.getTestTrace(getModel());
        if (testTrace != null) {
            return "testPrepare";
        } else {
            addFacesMessageFromResourceBundle("test.start.error.generation");
            return "testListForPass";
        }
    }

    public String startTest() {
        testTrace = testTraceService.startTest(testTrace);
        return doView("questionDetail");
    }
}
