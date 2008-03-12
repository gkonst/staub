package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.TestTrace;

import javax.ejb.Stateless;

/**
 * Stateless EJB Service for manipulations with <code>TestTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testTraceService")
@AutoCreate
@Stateless
public class TestTraceServiceBean extends GenericServiceBean<TestTrace, Integer> implements TestTraceService {
}
