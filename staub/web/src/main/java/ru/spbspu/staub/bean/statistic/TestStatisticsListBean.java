package ru.spbspu.staub.bean.statistic;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericExportableListBean;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.TestStatistics;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.export.Cell;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.CategoryService;
import ru.spbspu.staub.service.DisciplineService;
import ru.spbspu.staub.service.TestStatisticsService;
import ru.spbspu.staub.service.TopicService;

import java.util.List;

/**
 * Webbean for manipulating list data of <code>TestStatistics</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("testStatisticsListBean")
@Scope(SESSION)
public class TestStatisticsListBean extends GenericExportableListBean<TestStatistics> {
    private static final long serialVersionUID = -5787837250357305849L;

    @In
    private TestStatisticsService testStatisticsService;
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
        return testStatisticsService.find(formProperties, discipline, category, topic);
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
    public void doRefresh() {
        testStatisticsService.calculateStatistics();
        super.doRefresh();
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

    @Override
    public String[] getColumns() {
        return new String[]{"test.name", "test.category.discipline.name", "test.category.name", "totalAnswers", "correctAnswersPercent"};
    }

    @Override
    public String getBundlePrefix() {
        return "test.statistics.list.";
    }

    @Override
    protected List<Cell[]> getHeader() {
        List<Cell[]> header = super.getHeader();
        if (discipline != null) {
            header.add(new Cell[]{new Cell(getBundledString("test.statistics.list.test.category.discipline.name")), new Cell(discipline.getName())});
        }
        if (category != null) {
            header.add(new Cell[]{new Cell(getBundledString("test.statistics.list.test.category.name")), new Cell(category.getName())});
        }
        if (topic != null) {
            header.add(new Cell[]{new Cell(getBundledString("test.statistics.list.test.topics.name")), new Cell(topic.getName())});
        }
        return header;
    }
}
