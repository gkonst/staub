package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.GroupService;
import ru.spbspu.staub.service.StudentService;

import java.util.List;

/**
 * Webbean for manipulating list data of <code>Student</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("studentListBean")
@Scope(SESSION)
public class StudentListBean extends GenericListBean<Student> {
    private static final long serialVersionUID = 554953481314388855L;

    @In
    private StudentService studentService;

    @In
    private GroupService groupService;

    private List<Group> groupList;
    private Group group;

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
        fillGroups();
    }

    private void fillGroups() {
        groupList = groupService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            studentService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doCreate() {
        Contexts.getConversationContext().set(Group.class.getName(), group);
        return super.doCreate();
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
}
