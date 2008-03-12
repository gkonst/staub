package ru.spbspu.staub.bean;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.service.TestService;

import java.util.List;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testListBean")
@Scope(SESSION)
public class TestListBean extends GenericListBean<Test> {

    @In
    TestService testService;

    protected List<Test> getResultList() {
        return testService.findAll();
    }
}
