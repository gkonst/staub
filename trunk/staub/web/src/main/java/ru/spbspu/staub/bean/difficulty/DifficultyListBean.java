package ru.spbspu.staub.bean.difficulty;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.DifficultyService;

/**
 * Webbean for manipulating list data of <code>Difficulty</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("difficultyListBean")
@Scope(SESSION)
public class DifficultyListBean extends GenericListBean<Difficulty> {
    @In
    private DifficultyService difficultyService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return difficultyService.findAll(formProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            difficultyService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }
}
