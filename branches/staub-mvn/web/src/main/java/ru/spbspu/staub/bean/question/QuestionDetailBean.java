package ru.spbspu.staub.bean.question;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.service.DisciplineService;
import ru.spbspu.staub.service.QuestionService;
import ru.spbspu.staub.util.JAXBUtil;

import java.util.Date;
import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionDetailBean")
@Scope(SESSION)
public class QuestionDetailBean extends GenericDetailBean<Question> {

    @In
    private QuestionService questionService;

    @In
    private DisciplineService disciplineService;

    private QuestionType questionDefinition;

    private List<Discipline> disciplineList;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        fillDisciplineList();
        if (isCreateMode()) {
            setModel(new Question());
            setQuestionDefinition(new QuestionType());
        } else {
            setModel(questionService.findById(modelId));
            setQuestionDefinition(JAXBUtil.parseDefinitionXML(getModel().getDefinition()));
        }
    }

    private void fillDisciplineList() {
        disciplineList = disciplineService.findAll();
    }

    public void doSave() {
        logger.debug("Saving question...");
        String questionDefinitionXML = JAXBUtil.createDefinitionXML(getQuestionDefinition());
        getModel().setDefinition(questionDefinitionXML);
        if (isCreateMode()) {
            getModel().setCreated(new Date());
            getModel().setCreatedBy(Identity.instance().getUsername());
        }
        getModel().setModified(new Date());
        getModel().setModifiedBy(Identity.instance().getUsername());
        logger.debug(" question : #0", getModel());
        setModel(questionService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public void setDisciplineList(List<Discipline> disciplineList) {
        this.disciplineList = disciplineList;
    }

    public QuestionType getQuestionDefinition() {
        return questionDefinition;
    }

    public void setQuestionDefinition(QuestionType questionDefinition) {
        this.questionDefinition = questionDefinition;
    }
}
