package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Discipline;

import javax.ejb.Local;

/**
 * Local Interface for <code>DisciplineServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface DisciplineService extends GenericService<Discipline, Integer> {
    /**
     * Checks whether a code is not assigned to any discipline.
     *
     * @param code the code
     *
     * @return <code>true</code> if the code is unique; <code>false</code> otherwise
     */
    boolean isCodeUnique(String code);
}
