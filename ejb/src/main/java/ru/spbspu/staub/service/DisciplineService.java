package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Discipline;

import javax.ejb.Local;

/**
 * Local Interface for <code>DisciplineServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface DisciplineService extends GenericService<Discipline, Integer> {
}
