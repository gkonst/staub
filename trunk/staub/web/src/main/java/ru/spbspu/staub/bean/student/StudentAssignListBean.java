package ru.spbspu.staub.bean.student;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.AssignmentService;
import ru.spbspu.staub.service.GroupService;
import ru.spbspu.staub.service.StudentService;

import java.util.HashMap;
import java.util.List;

/**
 * Webbean for assigning students for specific test.
 *
 * @author Konstantin Grigoriev
 */
@Name("studentAssignListBean")
@Scope(SESSION)
public class StudentAssignListBean extends GenericListBean<User> {
    private static final long serialVersionUID = 3595505505814623943L;

    @RequestParameter("testId")
    private Integer injectedTestId;

    private Integer testId;

    @In
    private AssignmentService assignmentService;

    @In
    private StudentService studentService;

    @In
    private GroupService groupService;

    private List<Group> groups;

    private Group group;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initBean() {
        if (isBeanModeDefined()) {
            logger.debug("Preparing student assign bean...");
            testId = injectedTestId;
            // TODO not fully reset bean
            group = null;
            setSelectedMap(new HashMap<Object, Boolean>());
            setDataModel(null);
            fillGroups();
            logger.debug("Preparing student assign bean... OK");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return studentService.findStudents(formProperties, group);
    }

    /**
     * Assignes selected users for current test.
     */
    public void assign() {
        logger.debug(">>> Assigning students...");
        List<Integer> studentsIds = getSelectedItems();
        assignmentService.assignTest(testId, studentsIds, null, null);
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("student.assign.list.assignSuccess");
        logger.debug("<<< Assigning students...Ok");
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

    @Override
    public void doRefresh() {
        // TODO not fully reset bean
        if (group != null) {
            setSelectedMap(new HashMap<Object, Boolean>());
            super.doRefresh();
        } else {
            setDataModel(null);
        }
    }

    private void fillGroups() {
        groups = groupService.findAll();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
