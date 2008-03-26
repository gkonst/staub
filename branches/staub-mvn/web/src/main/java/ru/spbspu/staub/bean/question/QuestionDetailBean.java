package ru.spbspu.staub.bean.question;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.Messages;
import org.jboss.seam.security.Identity;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.question.AnswerType;
import ru.spbspu.staub.model.question.ChoiceType;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.service.*;
import ru.spbspu.staub.util.JAXBUtil;

import javax.faces.model.SelectItem;
import java.math.BigInteger;
import java.util.ArrayList;
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

    @In(required = false)
    private Test test;

    @In
    private QuestionService questionService;

    @In
    private DisciplineService disciplineService;

    @In
    private CategoryService categoryService;

    @In
    private DifficultyService difficultyService;

    @In
    private TestService testService;

    private QuestionType questionDefinition;

    private List<Discipline> disciplineList;

    private List<Category> categoryList;

    private List<Difficulty> difficultyList;

    private Object correctAnswer;

    private AnswerTypes answerType;

    private static enum AnswerTypes {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        fillDisciplineList();
        fillCategoryList();
        fillDifficultyList();
        if (isCreateMode()) {
            setModel(new Question());
            setQuestionDefinition(new QuestionType());
        } else {
            setModel(questionService.findById(modelId));
            setQuestionDefinition(JAXBUtil.parseQuestionXML(getModel().getDefinition()));
            determineAnswerType();
            determineCorrectAnswer();
        }
    }

    private void determineCorrectAnswer() {
        logger.debug(" determineCorrectAnswer...");
        if (AnswerTypes.SINGLE_CHOICE.equals(answerType)) {
            for (AnswerType type : questionDefinition.getSingleChoice().getAnswer()) {
                logger.debug("  checking answer : id=#0,value=#1,correct=#2", type.getId(), type.getValue(), type.getCorrect());
                if (Boolean.valueOf(type.getCorrect())) {
                    logger.debug("  !found correct answer : id=#0,value=#1", type.getId(), type.getValue());
                    setCorrectAnswer(type);
                }
            }
        } else if (AnswerTypes.SINGLE_CHOICE.equals(answerType)) {
            List<AnswerType> result = new ArrayList<AnswerType>();
            for (AnswerType type : questionDefinition.getSingleChoice().getAnswer()) {
                if (Boolean.valueOf(type.getCorrect())) {
                    result.add(type);
                }
            }
            setCorrectAnswer(result);
        } else {
            throw new IllegalArgumentException("Unrecognized answer type");
        }
        logger.debug(" determineCorrectAnswer...Ok");
    }

    private void determineAnswerType() {
        if (questionDefinition.getSingleChoice() != null) {
            setAnswerType(AnswerTypes.SINGLE_CHOICE);
        } else if (questionDefinition.getMultipleChoice() != null) {
            setAnswerType(AnswerTypes.MULTIPLE_CHOICE);
        } else {
            throw new IllegalArgumentException("Unrecognized answer type");
        }
    }

    private void fillDisciplineList() {
        disciplineList = disciplineService.findAll();
    }

    private void fillCategoryList() {
        categoryList = categoryService.findAll();
    }

    private void fillDifficultyList() {
        difficultyList = difficultyService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving question...");
        resolveCorrectAnswer();
        String questionDefinitionXML = JAXBUtil.createQuestionXML(getQuestionDefinition());
        getModel().setDefinition(questionDefinitionXML);
        if (isCreateMode()) {
            getModel().setCreated(new Date());
            getModel().setCreatedBy(Identity.instance().getUsername());
        }
        getModel().setModified(new Date());
        getModel().setModifiedBy(Identity.instance().getUsername());
        logger.debug(" question : #0", getModel());
        setModel(questionService.makePersistent(getModel()));
        if (isCreateMode() && test != null) {
            logger.debug(" creating question for test : #0", test);
            // TODO add specific test setting
            test = testService.findById(test.getId());
            test.getQuestions().add(getModel());
            testService.makePersistent(test);
        }
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    private void resolveCorrectAnswer() {
        if (AnswerTypes.SINGLE_CHOICE.equals(answerType)) {
            for (AnswerType type : questionDefinition.getSingleChoice().getAnswer()) {
                if (((AnswerType) getCorrectAnswer()).getId().equals(type.getId())) {
                    type.setCorrect(String.valueOf(Boolean.TRUE));
                }
            }
        } else if (AnswerTypes.MULTIPLE_CHOICE.equals(answerType)) {
            List<AnswerType> result = new ArrayList<AnswerType>();
            for (AnswerType type : questionDefinition.getMultipleChoice().getAnswer()) {
                for (AnswerType correctAnswer : (List<AnswerType>) getCorrectAnswer()) {
                    if (correctAnswer.getId().equals(type.getId())) {
                        type.setCorrect(String.valueOf(Boolean.TRUE));
                    }
                }
            }
            setCorrectAnswer(result);
        } else {
            throw new IllegalArgumentException("Unrecognized answer type");
        }
    }

    public List<SelectItem> getAnswerTypes() {
        List<SelectItem> result = new ArrayList<SelectItem>();
        for (AnswerTypes type : AnswerTypes.values()) {
            result.add(new SelectItem(type, Messages.instance().get("question.detail.answerType." + type)));
        }
        return result;
    }

    public void addAnswer() {
        logger.debug(">>> Adding answer...");
        if (questionDefinition.getSingleChoice() != null) {
            AnswerType newAnswer = new AnswerType();
            newAnswer.setId(new BigInteger(String.valueOf(questionDefinition.getSingleChoice().getAnswer().size() + 1)));
            logger.debug(" single choice type, id=#0, totalWas=#1", newAnswer.getId(), questionDefinition.getSingleChoice().getAnswer().size());
            questionDefinition.getSingleChoice().getAnswer().add(newAnswer);
        } else if (questionDefinition.getMultipleChoice() != null) {
            AnswerType newAnswer = new AnswerType();
            newAnswer.setId(new BigInteger(String.valueOf(questionDefinition.getMultipleChoice().getAnswer().size() + 1)));
            logger.debug(" multiple choice type, id=#0, totalWas=#1", newAnswer.getId(), questionDefinition.getMultipleChoice().getAnswer().size());
            questionDefinition.getMultipleChoice().getAnswer().add(newAnswer);
        }
        logger.debug(">>> Adding answer...Ok");
    }

    public void removeAnswer() {
        logger.debug(">>> Removing answer...");
        if (questionDefinition.getSingleChoice() != null) {
            int answerToRemoveId = questionDefinition.getSingleChoice().getAnswer().size() - 1;
            logger.debug(" single choice type, id=#0", answerToRemoveId);
            questionDefinition.getSingleChoice().getAnswer().remove(answerToRemoveId);
        } else if (questionDefinition.getMultipleChoice() != null) {
            int answerToRemoveId = questionDefinition.getMultipleChoice().getAnswer().size() - 1;
            logger.debug(" single choice type, id=#0", answerToRemoveId);
            questionDefinition.getMultipleChoice().getAnswer().remove(answerToRemoveId);
        }
        logger.debug(">>> Removing answer...Ok");
    }

    public void changeAnswerType() {
        logger.debug(">>> Changing answer type...#0", answerType);
        if (AnswerTypes.SINGLE_CHOICE.equals(answerType)) {
            questionDefinition.setSingleChoice(new ChoiceType());
            questionDefinition.setMultipleChoice(null);
            setCorrectAnswer(new AnswerType());
        } else if (AnswerTypes.MULTIPLE_CHOICE.equals(answerType)) {
            questionDefinition.setMultipleChoice(new ChoiceType());
            questionDefinition.setSingleChoice(null);
            setCorrectAnswer(new ArrayList<AnswerType>());
        } else {
            throw new IllegalArgumentException("Unrecognized answer type");
        }
        logger.debug(" new answer type=#0, correct answer type=#1", answerType, correctAnswer.getClass().getName());
        logger.debug(">>> Changing answer type...Ok");
    }

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public void setDisciplineList(List<Discipline> disciplineList) {
        this.disciplineList = disciplineList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Difficulty> getDifficultyList() {
        return difficultyList;
    }

    public void setDifficultyList(List<Difficulty> difficultyList) {
        this.difficultyList = difficultyList;
    }

    public QuestionType getQuestionDefinition() {
        return questionDefinition;
    }

    public void setQuestionDefinition(QuestionType questionDefinition) {
        this.questionDefinition = questionDefinition;
    }

    public Object getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Object correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public AnswerTypes getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerTypes answerType) {
        this.answerType = answerType;
    }
}
