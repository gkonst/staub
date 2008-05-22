package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.CategoryService;
import ru.spbspu.staub.service.DisciplineService;
import ru.spbspu.staub.service.TestService;
import ru.spbspu.staub.service.TopicService;

import java.util.List;

/**
 * Webbean for manipulating list data of <code>Test</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("testListBean")
@Scope(SESSION)
public class TestListBean extends GenericListBean<Test> {
    private static final long serialVersionUID = 6739840347539258651L;

    @In
    private TestService testService;
    @In
    private DisciplineService disciplineService;
    @In
    private CategoryService categoryService;
    @In
    private TopicService topicService;

    private List<Discipline> disciplineList;
    private List<Category> categoryList;
    private List<Topic> topicList;
    private Discipline discipline;
    private Category category;
    private Topic topic;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return testService.findTests(formProperties, discipline, category, topic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        fillDisciplineList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            testService.remove(getSelected());
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
        return super.doCreate();
    }

    public String showStudents() {
        Contexts.getConversationContext().set(Test.class.getName(), getSelected());
        return doView("studentAssignList");
    }

    public String showAssignments() {
        Contexts.getConversationContext().set(Test.class.getName(), getSelected());
        return doView("assignmentsList");
    }

    public void setDiscipline() {
        fillCategoryList();
        setCategory(null);
        setTopic(null);
        doRefresh();
    }

    public void setCategory() {
        fillTopicList();
        setTopic(null);
        doRefresh();
    }

    public void setTopic() {
        doRefresh();
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

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
