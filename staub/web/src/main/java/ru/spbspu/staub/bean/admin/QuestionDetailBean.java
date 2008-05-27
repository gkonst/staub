package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.richfaces.event.UploadEvent;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.AnswerWrapper;
import ru.spbspu.staub.model.ChoiceAnswerWrapper;
import ru.spbspu.staub.model.UserInputAnswerWrapper;
import ru.spbspu.staub.model.question.InputType;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.service.*;
import ru.spbspu.staub.util.ImageResource;

import java.io.*;
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

    @In
    private User user;

    @In
    private QuestionService questionService;

    @In
    private DisciplineService disciplineService;

    @In
    private CategoryService categoryService;

    @In
    private TopicService topicService;

    @In
    private DifficultyService difficultyService;

    private List<Discipline> disciplineList;
    private List<Category> categoryList;
    private List<Topic> topicList;
    private List<Difficulty> difficultyList;

    private AnswerWrapper answer;

    private AnswerWrapper.Type answerType;


    private Discipline discipline;
    private Category category;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        fillDisciplineList();
        fillDifficultyList();
        answerType = null;
        answer = null;
        if (isCreateMode()) {
            setModel(new Question());
            setQuestionDefinition(new QuestionType());
            setDiscipline((Discipline)Contexts.getConversationContext().get(Discipline.class.getName()));
            setCategory((Category)Contexts.getConversationContext().get(Category.class.getName()));
            getModel().setTopic((Topic)Contexts.getConversationContext().get(Topic.class.getName()));
            getModel().setDifficulty((Difficulty)Contexts.getConversationContext().get(Difficulty.class.getName()));
            setAnswerType(AnswerWrapper.Type.SINGLE_CHOICE);
            changeAnswerType();
        } else {
            setModel(questionService.findById(modelId));
            setCategory(getModel().getTopic().getCategory());
            setDiscipline(getModel().getTopic().getCategory().getDiscipline());
            answer = AnswerWrapper.getAnswer(getQuestionDefinition());
            answer.determineCorrectAnswer();
            setAnswerType(answer.getType());
        }
        refreshCategories();
        refreshTopics();
    }

    private void fillDisciplineList() {
        disciplineList = disciplineService.findAll();
    }

    public void refreshCategories() {
        if (discipline != null) {
            categoryList = categoryService.findCategories(discipline);
        } else {
            categoryList = null;
        }
    }

    public void refreshTopics() {
        if (category != null) {
            topicList = topicService.findTopics(category);
        } else {
            topicList = null;
        }
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
        answer.resolveCorrectAnswer();
        setModel(questionService.saveQuestion(getModel(), user));
        logger.debug("  Changing bean mode -> #0", BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("question.detail.saveSuccess", getModel().getId());
        logger.debug("Saving... OK");
    }

    public AnswerWrapper.Type[] getAnswerTypes() {
        return AnswerWrapper.Type.values();
    }

    public void changeAnswerType() {
        logger.debug(">>> Changing answer type...#0", answerType);
        answer = AnswerWrapper.createAnswer(getQuestionDefinition(), getAnswerType());
        if (answer.isChoice()) {
            ((ChoiceAnswerWrapper)answer).addAnswer();
            ((ChoiceAnswerWrapper)answer).addAnswer();
            ((ChoiceAnswerWrapper)answer).addAnswer();
            ((ChoiceAnswerWrapper)answer).addAnswer();
            ((ChoiceAnswerWrapper)answer).addAnswer();
        }
        logger.debug("<<< Changing answer type...Ok");
    }

    public void fileUploadListener(UploadEvent event) {
        logger.debug(">>> Uploading image...");
        logger.debug(" fileName : #0", event.getUploadItem().getFileName());
        String fileName = System.currentTimeMillis() + "_" + event.getUploadItem().getFileName();
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
            imageTag.append("/");
            imageTag.append(fileName);
            imageTag.append("\"/>");
            if (getQuestionDefinition().getDescription() == null) {
                getQuestionDefinition().setDescription("");
            }
            getQuestionDefinition().setDescription(getQuestionDefinition().getDescription() + imageTag);
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
        return getModel().getDefinition();
    }

    public void setQuestionDefinition(QuestionType questionDefinition) {
        getModel().setDefinition(questionDefinition);
    }

    public AnswerWrapper.Type getAnswerType() {
        return answerType;
    }

    public AnswerWrapper getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerWrapper answer) {
        this.answer = answer;
    }

    public void setAnswerType(AnswerWrapper.Type answerType) {
        this.answerType = answerType;
    }

    public String getUserInputRegex(String inputType) {
        return UserInputAnswerWrapper.REGEX_MAP.get(InputType.valueOf(inputType));
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}