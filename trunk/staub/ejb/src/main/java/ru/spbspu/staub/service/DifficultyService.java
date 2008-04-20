package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Difficulty;

import javax.ejb.Local;

/**
 * Local Interface for <code>DifficultyServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface DifficultyService extends GenericService<Difficulty, Integer> {

}