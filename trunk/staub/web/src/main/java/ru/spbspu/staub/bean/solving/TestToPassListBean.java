package ru.spbspu.staub.bean.solving;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.AssignmentService;

/**
 * Webbean for showing list data of <code>Assignment</code> entities
 * available for current student.
 *
 * @author Konstantin Grigoriev
 */
@Name("testToPassListBean")
@Scope(SESSION)
public class TestToPassListBean extends GenericListBean<Assignment> {
    private static final long serialVersionUID = 6262195551482944809L;

    @In
    private AssignmentService assignmentService;
    
    @In
    private Student student;

    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return assignmentService.findAssignments(formProperties, student);
    }
}
