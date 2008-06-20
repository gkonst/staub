package ru.spbspu.staub.service;

import ru.spbspu.staub.data.question.DisciplineDataType;
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

    /**
     * Imports a discipline.
     *
     * @param disciplineData the data to import
     *
     * @return the target entity
     */
    Discipline importDiscipline(DisciplineDataType disciplineData);

    /**
     * Exports a discipline.
     *
     * @param id the identification number
     *
     * @return the exported data
     */
    DisciplineDataType exportDiscipline(Integer id);
}
