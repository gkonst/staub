package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.AssignmentService;

/**
 * Webbean for manipulating list data of <code>Assignment</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("assignmentListBean")
@Scope(SESSION)
public class AssignmentListBean extends GenericListBean<Assignment> {
    private static final long serialVersionUID = -8594982122955965532L;

    @In
    private AssignmentService assignmentService;

    private Test test;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return assignmentService.find(formProperties, test);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        Test test = (Test) Contexts.getConversationContext().get(Test.class.getName());
        if (test != null) {
            this.test = test;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            assignmentService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }

    /**
     * Defines back operation for current bean.
     *
     * @return navigation outcome
     */
    public String doBack() {
        return "testList";
    }
}
