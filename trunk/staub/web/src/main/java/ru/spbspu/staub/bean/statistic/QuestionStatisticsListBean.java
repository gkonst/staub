package ru.spbspu.staub.bean.statistic;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.*;

import java.util.List;

/**
 * Webbean for manipulating list data of <code>QuestionStatistics</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionStatisticsListBean")
@Scope(SESSION)
public class QuestionStatisticsListBean extends GenericListBean<QuestionStatistics> {
    private static final long serialVersionUID = 741330532394106365L;

    @In
    private QuestionStatisticsService questionStatisticsService;
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
        return questionStatisticsService.find(formProperties, discipline, category, topic, difficulty, questionId);
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
    public void doRefresh() {
        questionStatisticsService.calculateStatistics();
        super.doRefresh();
    }

    private void fillDisciplineList() {
        disciplineList = disciplineService.findAll();
    }

    private void fillCategoryList() {
        if (discipline != null) {
            categoryList = categoryService.findCategories(discipline);
        } else {
            categoryList = null;
        }
    }

    private void fillTopicList() {
        if (category != null) {
            topicList = topicService.findTopics(category);
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
