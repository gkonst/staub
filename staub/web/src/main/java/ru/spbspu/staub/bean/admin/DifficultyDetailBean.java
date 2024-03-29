package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.service.DifficultyService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Webbean for manipulating detail data of <code>Difficulty</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("difficultyDetailBean")
@Scope(SESSION)
public class DifficultyDetailBean extends GenericDetailBean<Difficulty> {
    private static final long serialVersionUID = 4972878435691024078L;

    @In
    private DifficultyService difficultyService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        if (isCreateMode()) {
            setModel(new Difficulty());
        } else {
            setModel(difficultyService.findById(modelId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving difficulty...");
        setModel(difficultyService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    public void validateCode(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if ((isCreateMode() && !difficultyService.isCodeUnique((Integer) value)) ||
                (isEditMode() && !getModel().getCode().equals(value) && !difficultyService.isCodeUnique((Integer) value))) {
            throw new ValidatorException(new FacesMessage("Difficulty code must be unique"));
        }
    }
}

