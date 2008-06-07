package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.*;

import java.util.List;

/**
 * Webbean for manipulating list data of <code>Question</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionListBean")
@Scope(SESSION)
public class QuestionListBean extends GenericListBean<Question> {
    private static final long serialVersionUID = 5563120994479387998L;

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
    private Discipline discipline;
    private Category category;
    private Topic topic;
    private Difficulty difficulty;

    private Integer questionId;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return questionService.find(formProperties, discipline, category, topic, difficulty, questionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        fillDisciplineList();
        fillDifficultiesList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            questionService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doCreate() {
        Contexts.getConversationContext().set(Discipline.class.getName(), discipline);
        Contexts.getConversationContext().set(Category.class.getName(), category);
        Contexts.getConversationContext().set(Topic.class.getName(), topic);
        Contexts.getConversationContext().set(Difficulty.class.getName(), difficulty);
        return super.doCreate();
    }

    private void fillDisciplineList() {
        disciplineList = disciplineService.findAll();
    }

    private void fillCategoryList() {
        if (discipline != null) {
            categoryList = categoryService.find(discipline);
        } else {
            categoryList = null;
        }
    }

    private void fillTopicList() {
        if (category != null) {
            topicList = topicService.find(category);
        } else {
            topicList = null;
        }
    }

    private void fillDifficultiesList() {
        difficultyList = difficultyService.findAll();
    }

    public void setDiscipline() {
        fillCategoryList();
        setCategory(null);
        setTopic(null);
        setQuestionId(null);
        doRefresh();
    }

    public void setCategory() {
        fillTopicList();
        setTopic(null);
        setQuestionId(null);
        doRefresh();
    }

    public void setTopic() {
        setQuestionId(null);
        doRefresh();
    }

    public void setDifficulty() {
        setQuestionId(null);
        doRefresh();
    }

    public void setQuestionId() {
        setDiscipline(null);
        setCategory(null);
        setTopic(null);
        setDifficulty(null);
        doRefresh();
    }

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public List<Difficulty> getDifficultyList() {
        return difficultyList;
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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
