package ru.spbspu.staub.bean.test;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.In;
import static org.jboss.seam.ScopeType.SESSION;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.service.TestService;

/**
 * Webbean for manipulating detail data of <code>Test</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testDetailBean")
@Scope(SESSION)
public class TestDetailBean extends GenericDetailBean<Test> {

    @In
    private TestService testService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        if (isCreateMode()) {
            setModel(new Test());
        } else {
            setModel(testService.findById(modelId));
        }
    }
}