package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;

import javax.ejb.Stateless;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

/**
 * Stateless EJB Service for manipulations with <code>Test</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testService")
@AutoCreate
@Stateless
public class TestServiceBean extends GenericServiceBean<Test, Integer> implements TestService {
    public FormTable findAllToPassForUser(FormProperties formProperties, User user) {
        String query = "from Assignment a where a.testBegin <= :currentDate and a.testEnd > :currentDate";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("currentDate", new Date());
        return findAll(query, formProperties, parameters);
    }
}
