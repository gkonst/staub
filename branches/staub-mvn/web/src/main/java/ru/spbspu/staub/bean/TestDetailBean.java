package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import static org.jboss.seam.ScopeType.EVENT;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.dao.TestDAO;
import ru.spbspu.staub.entity.Test;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testDetailBean")
@Scope(EVENT)
public class TestDetailBean extends GenericDetailBean<Test> {

    @In
    private TestDAO testDAO;

    @Out(required = false, scope = ScopeType.CONVERSATION)
    private Integer testId;

    @Override
    protected void fillModel(Integer modelId) {
        setModel(testDAO.findById(modelId, false));
    }

    public String startTest() {
        this.testId = getModel().getId();
        return "testPrepare";
    }
}
