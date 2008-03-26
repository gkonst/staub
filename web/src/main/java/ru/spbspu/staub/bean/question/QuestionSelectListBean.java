package ru.spbspu.staub.bean.question;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.DataModels;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.QuestionService;

import javax.faces.model.DataModel;
import java.util.List;
import java.util.ArrayList;

/**
 * Webbean for manipulating list data of <code>Question</code> entities.
 * TODO
 *
 * @author Konstantin Grigoriev
 */
@Name("questionSelectListBean")
@Scope(SESSION)
public class QuestionSelectListBean extends GenericListBean<Question> {

    private DataModel selectedQuestions = DataModels.instance().getDataModel(new ArrayList<Object[]>());

    @In
    private Test test;

    @In
    private QuestionService questionService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return questionService.findAll(formProperties);
    }

    public void addQuestion() {
        logger.debug(">>> Adding question(#0)...", getSelected());
        getWrappedData().add(new Object[]{getSelected().getId(), getSelected().getName()});
        logger.debug("<<< Adding question...Ok");
    }

    public void removeQuestion() {
        logger.debug(">>> Removing question(#0)...", selectedQuestions.getRowIndex());
        getWrappedData().remove(selectedQuestions.getRowIndex());
        logger.debug("<<< Removing question...Ok");
    }

    public void removeAll() {
        logger.debug(">>> Removing all questions...");
        getWrappedData().clear();
        logger.debug("<<< Removing all questions...Ok");
    }

    public void doSave() {
        logger.debug(">>> Saving added questions for test(#0)...", test);
        
        logger.debug(">>> Saving added questions...Ok");
    }

    /**
     * Defines cancel operation for current bean.
     *
     * @return navigation outcome
     */
    public String doCancel() {
        return "testDetail";
    }

    /**
     * Defines back operation for current bean.
     *
     * @return navigation outcome
     */
    public String doBack() {
        return doRefresh("testDetail");
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> getWrappedData() {
        return ((List) selectedQuestions.getWrappedData());
    }

    public DataModel getSelectedQuestions() {
        return selectedQuestions;
    }

    public void setSelectedQuestions(DataModel selectedQuestions) {
        this.selectedQuestions = selectedQuestions;
    }
}
