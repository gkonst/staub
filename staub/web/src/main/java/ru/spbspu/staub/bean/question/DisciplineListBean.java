package ru.spbspu.staub.bean.question;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.DisciplineService;

/**
 * Webbean for manipulating list data of <code>Discipline</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("disciplineListBean")
@Scope(SESSION)
public class DisciplineListBean extends GenericListBean<Discipline> {
    @In
    private DisciplineService disciplineService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return disciplineService.findAll(formProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        disciplineService.remove(getSelected());
        doRefresh();
    }
}
