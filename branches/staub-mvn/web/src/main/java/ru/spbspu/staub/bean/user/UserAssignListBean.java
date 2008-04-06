package ru.spbspu.staub.bean.user;

import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.service.UserService;
import ru.spbspu.staub.service.TestService;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import static org.jboss.seam.ScopeType.SESSION;

import java.util.List;

/**
 * Webbean for assigning user for specific test.
 *
 * @author Konstantin Grigoriev
 */
@Name("userAssignListBean")
@Scope(SESSION)
public class UserAssignListBean extends GenericListBean<User> {

    @RequestParameter("testId")
    private Integer injectedTestId;

    private Integer testId;

    @In
    private UserService userService;

    @In
    private TestService testService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initBean() {
        if (isBeanModeDefined()) {
            logger.debug("Preparing list bean...");
            testId = injectedTestId;
            switch (getBeanMode()) {
                case VIEW_MODE:     // using fall through switch behaviour
                case EDIT_MODE:
                case CREATE_MODE:
                    findFirstPageData();
                    break;
                case REFRESH_MODE:
                    doRefresh();
                    break;
                default:
                    logger.debug("  Unknown bean mode -> skipping");
            }
            logger.debug("Preparing list bean... OK");
        }
    }

    /**
     * {@inheritDoc}
     */
    protected FormTable findObjects(FormProperties formProperties) {
        return userService.findUsersForAssign(formProperties);
    }

    /**
     * Assignes selected users for current test.
     */
    public void assign() {
        logger.debug(">>> Assigning users...");
        List<Integer> usersIds = getSelectedItems();
        testService.assignTest(testId, usersIds);
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("user.assign.list.assignSuccess");
        logger.debug("<<< Assigning users...Ok");
    }

    /**
     * Defines cancel operation for current bean.
     *
     * @return navigation outcome
     */
    public String doCancel() {
        return "testList";
    }

    /**
     * Defines back operation for current bean.
     *
     * @return navigation outcome
     */
    public String doBack() {
        return "testList";
    }
}
