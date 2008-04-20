package ru.spbspu.staub.bean.test;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.TestService;
import ru.spbspu.staub.service.AssignmentService;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testToPassListBean")
@Scope(SESSION)
public class TestToPassListBean extends GenericListBean<Test> {

    @In
    private AssignmentService assignmentService;

    @In
    private Student student;

    protected FormTable findObjects(FormProperties formProperties) {
        return assignmentService.findAssigned(formProperties, student);
    }
}
