package ru.spbspu.staub.bean.statistic;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericExportableListBean;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.QuestionTraceService;

/**
 * Webbean for manipulating list data of <code>QuestionTrace</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionTraceListBean")
@Scope(SESSION)
public class QuestionTraceListBean extends GenericExportableListBean<QuestionTrace> {
    private static final long serialVersionUID = 421912817436463611L;

    @In
    private QuestionTraceService questionTraceService;

    private TestTrace testTrace;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return questionTraceService.find(formProperties, testTrace);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        TestTrace testTrace = (TestTrace) Contexts.getConversationContext().get(TestTrace.class.getName());
        if (testTrace != null) {
            this.testTrace = testTrace;
        }
    }

    @Override
    public String getBundlePrefix() {
        return "question.trace.list.";
    }

    @Override
    public String[] getColumns() {
        return new String[]{"question.id", "totalTime", "correct"};
    }
}
