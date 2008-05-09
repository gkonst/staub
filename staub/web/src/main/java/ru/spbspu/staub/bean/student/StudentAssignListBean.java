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

import java.util.Calendar;
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

    private Date testBegin;
    private Date testEnd;

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
        // testEnd default value - current date and time
        testBegin = new Date();
        Calendar testEnd = Calendar.getInstance();
        testEnd.clear(Calendar.HOUR_OF_DAY);
        testEnd.clear(Calendar.MINUTE);
        testEnd.clear(Calendar.SECOND);
        testEnd.clear(Calendar.MILLISECOND);
        testEnd.add(Calendar.DAY_OF_MONTH, 1);
        // testBegin default value - end of current day
        this.testEnd = testEnd.getTime();
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
        if(testBegin.after(testEnd)) {
            addFacesMessageFromResourceBundle("student.assign.list.validation.dates");
            logger.debug("<<< Assigning students...failed");
            return;
        }
        List<Integer> studentsIds = getSelectedItems();
        assignmentService.assignTest(test.getId(), studentsIds, testBegin, testEnd);
        getSelectedMap().clear();
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

    public Date getTestBegin() {
        return testBegin;
    }

    public void setTestBegin(Date testBegin) {
        this.testBegin = testBegin;
    }

    public Date getTestEnd() {
        return testEnd;
    }

    public void setTestEnd(Date testEnd) {
        this.testEnd = testEnd;
    }
}
