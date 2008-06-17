package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.service.GroupService;
import ru.spbspu.staub.service.StudentService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Student</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("studentDetailBean")
@Scope(SESSION)
public class StudentDetailBean extends GenericDetailBean<Student> {
    private static final long serialVersionUID = 7860014639037606020L;

    @In
    private StudentService studentService;

    @In
    private GroupService groupService;

    private List<Group> groupList;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        fillGroupList();
        if (isCreateMode()) {
            setModel(new Student());
            Group group = (Group) Contexts.getConversationContext().get(Group.class.getName());
            if (group != null) {
                getModel().setGroup(group);
            }
        } else {
            setModel(studentService.findById(modelId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving student...");
        setModel(studentService.save(getModel()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    public void validateCode(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if ((isCreateMode() && !studentService.isCodeUnique(String.valueOf(value))) ||
                (isEditMode() && !getModel().getCode().equals(value) && !studentService.isCodeUnique(String.valueOf(value)))) {
            throw new ValidatorException(new FacesMessage("Student code must be unique"));
        }
    }

    private void fillGroupList() {
        groupList = groupService.findAll();
    }

    public List<Group> getGroupList() {
        return groupList;
    }
}
