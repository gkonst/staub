package ru.spbspu.staub.bean.statistic;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericExportableListBean;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.export.Cell;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.CategoryService;
import ru.spbspu.staub.service.DisciplineService;
import ru.spbspu.staub.service.GroupService;
import ru.spbspu.staub.service.TopicService;
import ru.spbspu.staub.statistics.GroupStatistics;

import java.util.Date;
import java.util.List;

/**
 * Webbean for manipulating list data of <code>GroupStatistics</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("groupStatisticsListBean")
@Scope(SESSION)
public class GroupStatisticsListBean extends GenericExportableListBean<GroupStatistics> {
    private static final long serialVersionUID = -3399176236615087021L;
    @In
    private GroupService groupService;
    @In
    private DisciplineService disciplineService;
    @In
    private CategoryService categoryService;
    @In
    private TopicService topicService;

    private Group group;
    private Discipline discipline;
    private Category category;
    private Topic topic;
    private Date begin;
    private Date end;

    private List<Discipline> disciplineList;
    private List<Category> categoryList;
    private List<Topic> topicList;
    private List<Group> groupList;

    protected FormTable findObjects(FormProperties formProperties) {
        return groupService.getStatistics(formProperties, group, discipline, category, topic, begin, end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        fillDisciplineList();
        fillGroupList();
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

    public void setGroup() {
        doRefresh();
    }

    public void setStarted() {
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

    private void fillGroupList() {
        groupList = groupService.findAll();
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

    public List<Group> getGroupList() {
        return groupList;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String[] getColumns() {
        return new String[]{"test.name", "test.category.discipline.name", "test.category.name", "totalAnswers", "correctAnswersPercent"};
    }

    @Override
    public String getBundlePrefix() {
        return "group.statistics.list.";
    }

    @Override
    protected List<Cell[]> getHeader() {
        List<Cell[]> header = super.getHeader();
        if (discipline != null) {
            header.add(new Cell[]{new Cell(getBundledString("group.statistics.list.test.category.discipline.name")), new Cell(discipline.getName())});
        }
        if (category != null) {
            header.add(new Cell[]{new Cell(getBundledString("group.statistics.list.test.category.name")), new Cell(category.getName())});
        }
        if (topic != null) {
            header.add(new Cell[]{new Cell(getBundledString("group.statistics.list.test.topics.name")), new Cell(topic.getName())});
        }
        if (group != null) {
            header.add(new Cell[]{new Cell(getBundledString("group.statistics.list.group.name")), new Cell(group.getName())});
        }
        return header;
    }
}
