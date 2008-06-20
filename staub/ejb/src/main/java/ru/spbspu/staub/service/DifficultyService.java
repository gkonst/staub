package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.data.question.DifficultyDataType;

import javax.ejb.Local;

/**
 * Local Interface for <code>DifficultyServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface DifficultyService extends GenericService<Difficulty, Integer> {
    /**
     * Checks whether a code is not assigned to any difficulty level.
     *
     * @param code the code
     *
     * @return <code>true</code> if the code is unique; <code>false</code> otherwise
     */
    boolean isCodeUnique(Integer code);

    /**
     * Imports a difficulty level.
     *
     * @param difficultyData the data to import
     *
     * @return the target entity
     */
    Difficulty importDifficulty(DifficultyDataType difficultyData);

    /**
     * Exports a difficulty level.
     *
     * @param id the identification number
     *
     * @return the exported data
     */
    DifficultyDataType exportDifficulty(Integer id);
}