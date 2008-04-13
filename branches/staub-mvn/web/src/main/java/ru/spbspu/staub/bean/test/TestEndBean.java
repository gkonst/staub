package ru.spbspu.staub.bean.test;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.service.TestTraceService;

/**
 * TODO add description
 *
 * @author Konstantin Grigoriev
 */
@Name("testEndBean")
@Scope(SESSION)
public class TestEndBean extends GenericDetailBean<TestTrace> {

    @In
    private TestTraceService testTraceService;

    protected void fillModel(Integer modelId) {
        setModel(testTraceService.findById(modelId));    
    }
}
