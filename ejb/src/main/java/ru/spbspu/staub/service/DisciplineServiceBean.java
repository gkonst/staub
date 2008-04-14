package ru.spbspu.staub.service;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.AutoCreate;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.interceptors.CallLogger;

/**
 * Stateless EJB Service for manipulations with <code>Discipline</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("disciplineService")
@AutoCreate
@Interceptors({CallLogger.class})
@Stateless
public class DisciplineServiceBean extends GenericServiceBean<Discipline, Integer> implements DisciplineService {
}
