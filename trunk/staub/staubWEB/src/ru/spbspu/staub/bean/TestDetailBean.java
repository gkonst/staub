package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.dao.TestDAO;
import ru.spbspu.staub.entity.Test;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testMainBean")
public class TestDetailBean extends GenericDetailBean<Test> {

    @In
    private TestDAO testDAO;

    protected void fillModel(Long modelId) {
        setModel(testDAO.findById(modelId, false));
    }
}
