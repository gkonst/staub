package ru.spbspu.staub.bean;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.dao.TestDAO;
import ru.spbspu.staub.entity.Test;

import java.util.List;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testListBean")
@Scope(SESSION)
public class TestListBean extends GenericListBean<Test> {

    @In TestDAO testDAO;

    protected List<Test> getResultList() {
        return testDAO.findAll();
    }
}
