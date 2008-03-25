package ru.spbspu.staub.bean.test;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.international.Messages;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.SelectorEnum;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.QuestionService;
import ru.spbspu.staub.service.TestService;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Test</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testDetailBean")
@Scope(SESSION)
public class TestDetailBean extends GenericListBean<Question> {

    @RequestParameter
    private Integer modelId;

    @Out(value = "test", required = false)
    private Test model;

    @In
    private TestService testService;

    @In
    private QuestionService questionService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initBean() {
        if (isBeanModeDefined()) {
            logger.debug("Preparing list bean...");
            switch (getBeanMode()) {
                case VIEW_MODE:     // using fall through switch behaviour
                case EDIT_MODE:
                    fillModel(modelId);
                    findFirstPageData();
                    break;
                case CREATE_MODE:
                    fillModel(modelId);
                    break;
                case REFRESH_MODE:
                    doRefresh();
                    break;
                default:
                    logger.debug("  Unknown bean mode -> skipping");
            }
            logger.debug("Preparing list bean... OK");
        }
    }

    private void fillModel(Integer modelId) {
        if (isCreateMode()) {
            setModel(new Test());
        } else {
            setModel(testService.findById(modelId));
        }
    }

    protected FormTable findObjects(FormProperties formProperties) {
        return questionService.findAllForTest(formProperties, getModel());
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

    public String doCreate() {
        return doCreate("detail");
    }

    public void doSave() {
        logger.debug("Saving test...");
        setModel(testService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    public Test getModel() {
        return model;
    }

    public void setModel(Test model) {
        this.model = model;
    }
}