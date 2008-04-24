package ru.spbspu.staub.bean.difficulty;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.service.DifficultyService;

/**
 * Webbean for manipulating detail data of <code>Difficulty</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("difficultyDetailBean")
@Scope(SESSION)
public class DifficultyDetailBean extends GenericDetailBean<Difficulty> {

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
}

