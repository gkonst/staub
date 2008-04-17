package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.entity.Test;

import javax.ejb.Local;
import java.util.List;

/**
 * Local Interface for <code>DifficultyServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface DifficultyService extends GenericService<Difficulty, Integer> {
    /**
     * Returns questions difficulty levels of a test.
     *
     * @param test the test
     *
     * @return the difficulty levels list
     */
    List<Difficulty> getDifficultyLevels(Test test);
}