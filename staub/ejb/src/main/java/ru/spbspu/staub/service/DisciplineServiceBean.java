package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Discipline;

import javax.ejb.Stateless;

/**
 * Stateless EJB Service for manipulations with <code>Discipline</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("disciplineService")
@AutoCreate
@Stateless
public class DisciplineServiceBean extends GenericServiceBean<Discipline, Integer> implements DisciplineService {
    
}
