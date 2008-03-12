package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.service.TestService;

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

    @Out(required = false, scope = ScopeType.CONVERSATION)
    private Integer testId;

    @Override
    protected void fillModel(Integer modelId) {
        setModel(testService.findById(modelId, false));
    }

    public String startTest() {
        this.testId = getModel().getId();
        return "testPrepare";
    }
}
