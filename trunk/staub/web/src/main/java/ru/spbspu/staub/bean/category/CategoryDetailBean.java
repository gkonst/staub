package ru.spbspu.staub.bean.category;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.In;
import static org.jboss.seam.ScopeType.SESSION;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.service.CategoryService;
import ru.spbspu.staub.service.DisciplineService;

import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Category</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("categoryDetailBean")
@Scope(SESSION)
public class CategoryDetailBean extends GenericDetailBean<Category> {

    @In
    private CategoryService categoryService;

    @In
    private DisciplineService disciplineService;

    private List<Discipline> disciplineList;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        fillDisciplineList();
        if (isCreateMode()) {
            setModel(new Category());
        } else {
            setModel(categoryService.findById(modelId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving category...");
        setModel(categoryService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    private void fillDisciplineList() {
        disciplineList = disciplineService.findAll();
    }

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }
}
