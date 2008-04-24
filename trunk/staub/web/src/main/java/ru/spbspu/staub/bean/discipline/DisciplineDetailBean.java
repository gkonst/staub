package ru.spbspu.staub.bean.discipline;

import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.service.DisciplineService;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.In;
import static org.jboss.seam.ScopeType.SESSION;

/**
 * Webbean for manipulating detail data of <code>Discipline</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("disciplineDetailBean")
@Scope(SESSION)
public class DisciplineDetailBean extends GenericDetailBean<Discipline> {

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
}
