package ru.spbspu.staub.bean.question;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.DataModels;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.QuestionService;
import ru.spbspu.staub.service.TestService;

import javax.faces.model.DataModel;
import java.util.ArrayList;
import java.util.List;

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
    private User user;

    @In(value = "test", required = false)
    private Test injectedTest;

    private Test test;

    @In
    private QuestionService questionService;

    @In
    private TestService testService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initBean() {
        if (isBeanModeDefined()) {
            logger.debug("Preparing list bean...");
            selectedQuestions = DataModels.instance().getDataModel(new ArrayList<Object[]>());
            if(injectedTest == null) {
                throw new IllegalArgumentException("Test must be injected for using this bean");
            } else {
                test = injectedTest;
            }
            switch (getBeanMode()) {
                case VIEW_MODE:     // using fall through switch behaviour
                case EDIT_MODE:
                case CREATE_MODE:
                    findFirstPageData();
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

    public void addSelected() {
        logger.debug(">>> Adding selected items...#0", getSelectedItems().size());
        for (Object item : getSelectedItems()) {
            Question questionItem = (Question) item;
            getWrappedData().add(new Object[]{questionItem.getId(), questionItem.getName()});
        }
        getSelectedMap().clear();
        logger.debug("<<< Adding selected items...");
    }

    public void doSave() {
        logger.debug(">>> Saving added questions for test(#0)...", test);
        List<Integer> questionsIds = new ArrayList<Integer>();
        for (Object[] selectedQuestion : getWrappedData()) {
            questionsIds.add((Integer) selectedQuestion[0]);
        }
        test = testService.addQuestionsToTest(test, questionsIds, user);
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
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
        return doEdit("testDetail");
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
