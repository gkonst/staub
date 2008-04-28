package ru.spbspu.staub.bean.topic;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.service.CategoryService;
import ru.spbspu.staub.service.DisciplineService;
import ru.spbspu.staub.service.TopicService;

import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Topic</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("topicDetailBean")
@Scope(SESSION)
public class TopicDetailBean extends GenericDetailBean<Topic> {
    private static final long serialVersionUID = 2741355957669534823L;

    @In
    private DisciplineService disciplineService;

    @In
    private CategoryService categoryService;

    @In
    private TopicService topicService;

    private List<Discipline> disciplineList;

    private List<Category> categoryList;

    private Discipline discipline;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        fillDisciplineList();
        if (isCreateMode()) {
            setModel(new Topic());
        } else {
            setModel(topicService.findById(modelId));
            setDiscipline(getModel().getCategory().getDiscipline());
        }
        refreshCategories();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving topic...");
        setModel(topicService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
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

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
