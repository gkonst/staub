package ru.spbspu.staub.bean.student;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.AssignmentService;
import ru.spbspu.staub.service.GroupService;
import ru.spbspu.staub.service.StudentService;

import java.util.Date;
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

    private Test test;

    @In
    private AssignmentService assignmentService;

    @In
    private StudentService studentService;

    @In
    private GroupService groupService;

    private List<Group> groupList;

    private Group group;

    private Date startDate;
    private Date endDate;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return studentService.findStudents(formProperties, group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        // TODO initialize start and end dates
        fillGroups();
        Test test = (Test) Contexts.getConversationContext().get(Test.class.getName());
        if (test != null) {
            this.test = test;
        }
    }

    /**
     * Assignes selected users for current test.
     */
    public void assign() {
        logger.debug(">>> Assigning students...");
        List<Integer> studentsIds = getSelectedItems();
        // TODO check starDate > end Date
        assignmentService.assignTest(test.getId(), studentsIds, startDate, endDate);
        addFacesMessageFromResourceBundle("student.assign.list.assignSuccess");
        logger.debug("<<< Assigning students...Ok");
    }

    /**
     * Defines back operation for current bean.
     *
     * @return navigation outcome
     */
    public String doBack() {
        return "testList";
    }

    private void fillGroups() {
        groupList = groupService.findAll();
    }

    public void setGroup() {
        doRefresh();
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
