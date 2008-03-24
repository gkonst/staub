package ru.spbspu.staub.bean.test;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.In;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.international.Messages;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.SelectorEnum;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.service.TestService;

import javax.faces.model.SelectItem;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

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

    public List<SelectItem> getSelectorTypes() {
        List<SelectItem> result = new ArrayList<SelectItem>();
        for (SelectorEnum type : SelectorEnum.values()) {
            result.add(new SelectItem(type, Messages.instance().get("test.detail.selectorType." + type)));
        }
        return result;
    }

    public boolean isAllSelectorType() {
        return SelectorEnum.ALL.equals(getModel().getSelectorType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving test...");
        setModel(testService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    public String showQuestions() {
        ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).setAttribute("test", getModel());
        return "questions";
    }
}