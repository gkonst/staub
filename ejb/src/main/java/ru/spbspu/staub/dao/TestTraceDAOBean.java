package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.TestTrace;

import javax.ejb.Stateless;

/**
 * Stateless EJB DAO for manipulations with <code>TestTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testTraceDAO")
@AutoCreate
@Stateless
public class TestTraceDAOBean extends GenericDAOBean<TestTrace, Integer> implements TestTraceDAO {
}
