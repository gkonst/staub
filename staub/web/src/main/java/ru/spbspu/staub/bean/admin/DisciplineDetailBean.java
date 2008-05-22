package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.service.DisciplineService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Webbean for manipulating detail data of <code>Discipline</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("disciplineDetailBean")
@Scope(SESSION)
public class DisciplineDetailBean extends GenericDetailBean<Discipline> {
    private static final long serialVersionUID = -4702431357733560796L;

    @In
    private DisciplineService disciplineService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        if (isCreateMode()) {
            setModel(new Discipline());
        } else {
            setModel(disciplineService.findById(modelId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving discipline...");
        setModel(disciplineService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    public void validateCode(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (!disciplineService.isCodeUnique(String.valueOf(value))) {
            throw new ValidatorException(new FacesMessage("Discipline code must be unique"));
        }
    }
}
