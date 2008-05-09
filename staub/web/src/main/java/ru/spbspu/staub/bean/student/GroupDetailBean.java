package ru.spbspu.staub.bean.student;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.service.GroupService;

/**
 * Webbean for manipulating detail data of <code>Group</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("groupDetailBean")
@Scope(SESSION)
public class GroupDetailBean extends GenericDetailBean<Group> {
    private static final long serialVersionUID = -8391481196457853709L;

    @In
    private GroupService groupService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        if (isCreateMode()) {
            setModel(new Group());
        } else {
            setModel(groupService.findById(modelId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving group...");
        setModel(groupService.makePersistent(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }
}
