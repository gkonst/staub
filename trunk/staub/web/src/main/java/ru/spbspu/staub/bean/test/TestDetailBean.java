package ru.spbspu.staub.bean.test;

import org.jboss.seam.ScopeType;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.service.TestService;

/**
 * Webbean for manipulating detail data of <code>Test</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testDetailBean")
@Scope(SESSION)
public class TestDetailBean extends GenericDetailBean<Test> {

    @RequestParameter
    private Integer modelId;

    @Out(value = "test", scope = ScopeType.EVENT, required = false)
    private Test model;

    @In
    private User user;

    @In
    private TestService testService;

    protected void fillModel(Integer modelId) {
        if (isCreateMode()) {
            setModel(new Test());
            getModel().setPassScore(50);
        } else {
            setModel(testService.findById(modelId));
        }
    }

    public void doSave() {
        logger.debug("Saving test...");
        setModel(testService.saveTest(getModel(), user));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }
}