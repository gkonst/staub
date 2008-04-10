package ru.spbspu.staub.bean.question;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.event.UploadEvent;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.question.AnswerType;
import ru.spbspu.staub.model.question.ChoiceType;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.service.*;
import ru.spbspu.staub.util.ImageResource;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionDetailBean")
@Scope(SESSION)
public class QuestionDetailBean extends GenericDetailBean<Question> {
    private static final long serialVersionUID = -458598915895087232L;

    @In(value = "test", required = false)
    private Test injectedTest;

    private Test test;

    @In
    private User user;

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

    private List<Discipline> disciplineList;
    private List<Category> categoryList;
    private List<Difficulty> difficultyList;

    private Object correctAnswer;

    private AnswerTypes answerType;

    private static enum AnswerTypes {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE
    }

    private String imageTag;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        test = injectedTest;
        fillDisciplineList();
        fillCategoryList();
        fillDifficultyList();
        answerType = null;
        correctAnswer = null;
        if (isCreateMode()) {
            setModel(new Question());
            setQuestionDefinition(new QuestionType());
        } else {
            setModel(questionService.findById(modelId));
            determineAnswerType();
            determineCorrectAnswer();
        }
    }

    private void determineCorrectAnswer() {
        logger.debug(" determineCorrectAnswer...");
        if (AnswerTypes.SINGLE_CHOICE.equals(answerType)) {
            for (AnswerType type : getQuestionDefinition().getSingleChoice().getAnswer()) {
                logger.debug("  checking answer : id=#0,value=#1,correct=#2", type.getId(), type.getValue(), type.getCorrect());
                if (Boolean.valueOf(type.getCorrect())) {
                    logger.debug("  !found correct answer : id=#0,value=#1", type.getId(), type.getValue());
                    setCorrectAnswer(type);
                }
            }
        } else if (AnswerTypes.MULTIPLE_CHOICE.equals(answerType)) {
            List<AnswerType> result = new ArrayList<AnswerType>();
            for (AnswerType type : getQuestionDefinition().getMultipleChoice().getAnswer()) {
                if (Boolean.valueOf(type.getCorrect())) {
                    result.add(type);
                }
            }
            setCorrectAnswer(result);
        } else {
            throw new IllegalArgumentException("Unrecognized answer type : " + answerType);
        }
        logger.debug(" determineCorrectAnswer...Ok");
    }

    private void determineAnswerType() {
        if (getQuestionDefinition().getSingleChoice() != null) {
            setAnswerType(AnswerTypes.SINGLE_CHOICE);
        } else if (getQuestionDefinition().getMultipleChoice() != null) {
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
        setModel(questionService.saveQuestion(getModel(), user));
        if (test != null && isCreateMode()) {
            logger.debug(" adding question to test : #0", test);
            test = testService.addQuestionToTest(test, getModel(), user);
        }
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doCancel() {
        if (test != null) {
            return "testDetail";
        } else {
            return super.doCancel();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doBack() {
        if (test != null) {
            return doRefresh("testDetail");
        } else {
            return super.doBack();
        }
    }

    private void resolveCorrectAnswer() {
        if (AnswerTypes.SINGLE_CHOICE.equals(answerType)) {
            for (AnswerType type : getQuestionDefinition().getSingleChoice().getAnswer()) {
                if (((AnswerType) getCorrectAnswer()).getId().equals(type.getId())) {
                    type.setCorrect(String.valueOf(Boolean.TRUE));
                }
            }
        } else if (AnswerTypes.MULTIPLE_CHOICE.equals(answerType)) {
            List<AnswerType> result = new ArrayList<AnswerType>();
            for (AnswerType type : getQuestionDefinition().getMultipleChoice().getAnswer()) {
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

    public AnswerTypes[] getAnswerTypes() {
        return AnswerTypes.values();
    }

    public void addAnswer() {
        logger.debug(">>> Adding answer...");
        if (getQuestionDefinition().getSingleChoice() != null) {
            AnswerType newAnswer = new AnswerType();
            newAnswer.setId(new BigInteger(String.valueOf(getQuestionDefinition().getSingleChoice().getAnswer().size() + 1)));
            logger.debug(" single choice type, id=#0, totalWas=#1", newAnswer.getId(), getQuestionDefinition().getSingleChoice().getAnswer().size());
            getQuestionDefinition().getSingleChoice().getAnswer().add(newAnswer);
        } else if (getQuestionDefinition().getMultipleChoice() != null) {
            AnswerType newAnswer = new AnswerType();
            newAnswer.setId(new BigInteger(String.valueOf(getQuestionDefinition().getMultipleChoice().getAnswer().size() + 1)));
            logger.debug(" multiple choice type, id=#0, totalWas=#1", newAnswer.getId(), getQuestionDefinition().getMultipleChoice().getAnswer().size());
            getQuestionDefinition().getMultipleChoice().getAnswer().add(newAnswer);
        }
        logger.debug("<<< Adding answer...Ok");
    }

    public void removeAnswer() {
        logger.debug(">>> Removing answer...");
        if (getQuestionDefinition().getSingleChoice() != null) {
            int answerToRemoveId = getQuestionDefinition().getSingleChoice().getAnswer().size() - 1;
            logger.debug(" single choice type, id=#0", answerToRemoveId);
            getQuestionDefinition().getSingleChoice().getAnswer().remove(answerToRemoveId);
        } else if (getQuestionDefinition().getMultipleChoice() != null) {
            int answerToRemoveId = getQuestionDefinition().getMultipleChoice().getAnswer().size() - 1;
            logger.debug(" single choice type, id=#0", answerToRemoveId);
            getQuestionDefinition().getMultipleChoice().getAnswer().remove(answerToRemoveId);
        }
        logger.debug("<<< Removing answer...Ok");
    }

    public void changeAnswerType() {
        logger.debug(">>> Changing answer type...#0", answerType);
        if (AnswerTypes.SINGLE_CHOICE.equals(answerType)) {
            getQuestionDefinition().setSingleChoice(new ChoiceType());
            getQuestionDefinition().setMultipleChoice(null);
            setCorrectAnswer(new AnswerType());
        } else if (AnswerTypes.MULTIPLE_CHOICE.equals(answerType)) {
            getQuestionDefinition().setMultipleChoice(new ChoiceType());
            getQuestionDefinition().setSingleChoice(null);
            setCorrectAnswer(new ArrayList<AnswerType>());
        } else {
            throw new IllegalArgumentException("Unrecognized answer type");
        }
        addAnswer();
        logger.debug(" new answer type=#0, correct answer type=#1", answerType, correctAnswer.getClass().getName());
        logger.debug("<<< Changing answer type...Ok");
    }

    public void fileUploadListener(UploadEvent event) {
        logger.debug(">>> Uploading image...");
        logger.debug(" fileName : #0", event.getUploadItem().getFileName());
        String fileName = event.getUploadItem().getFileName();
        String filePath = ImageResource.getResourceDirectory() + File.separator + fileName;
        logger.debug(" filePath : #0", filePath);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            File file = new File(filePath);
            file.createNewFile();
            fi = new FileInputStream(event.getUploadItem().getFile());
            fo = new FileOutputStream(file);            
            byte[] buf = new byte[100];
            int size;
            while ((size = fi.read(buf)) > 0) {
                fo.write(buf, 0, size);
            }
            fo.flush();
            StringBuilder imageTag = new StringBuilder();
            imageTag.append("<img src=\"");
            imageTag.append(getRequest().getContextPath());
            imageTag.append("/seam/resource");
            imageTag.append(ImageResource.RESOURCE_PATH);
            imageTag.append(fileName);
            imageTag.append("\"/>");
        } catch (FileNotFoundException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            closeStream(fi);
            closeStream(fo);
        }
        logger.debug("<<< Uploading image...Ok");
    }

    private void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.error("error suring stream closing", e);
            }
        }
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
        return getModel().getQuestion();
    }

    public void setQuestionDefinition(QuestionType questionDefinition) {
        getModel().setQuestion(questionDefinition);
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

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }
}
