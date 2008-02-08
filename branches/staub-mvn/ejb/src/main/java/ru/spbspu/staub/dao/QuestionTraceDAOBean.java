package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.QuestionTrace;

import javax.ejb.Stateless;

/**
 * Stateless EJB DAO for manipulations with <code>QuestionTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionTraceDAO")
@AutoCreate
@Stateless
public class QuestionTraceDAOBean extends GenericDAOBean<QuestionTrace, Integer> implements QuestionTraceDAO {
}
