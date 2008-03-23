package ru.spbspu.staub.bean.test;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.In;
import static org.jboss.seam.ScopeType.SESSION;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.service.TestService;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testListBean")
@Scope(SESSION)
public class TestListBean extends GenericListBean<Test> {

    @In
    private TestService testService;

    protected FormTable findObjects(FormProperties formProperties) {
        return testService.findAll(formProperties);
    }
}
