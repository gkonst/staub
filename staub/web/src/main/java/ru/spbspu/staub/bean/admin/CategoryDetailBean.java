package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.service.CategoryService;
import ru.spbspu.staub.service.DisciplineService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Category</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("categoryDetailBean")
@Scope(SESSION)
public class CategoryDetailBean extends GenericDetailBean<Category> {
    private static final long serialVersionUID = 7860014639037606020L;

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
            Discipline discipline = (Discipline) Contexts.getConversationContext().get(Discipline.class.getName());
            if (discipline != null) {
                getModel().setDiscipline(discipline);
            }
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

    public void validateCode(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if ((isCreateMode() && !categoryService.isCodeUnique(String.valueOf(value))) ||
                (isEditMode() && !getModel().getCode().equals(value) && !categoryService.isCodeUnique(String.valueOf(value)))) {
            throw new ValidatorException(new FacesMessage("Category code must be unique"));
        }
    }
}
