package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Difficulty;

import javax.ejb.Local;

/**
 * Local Interface for <code>DifficultyServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface DifficultyService extends GenericService<Difficulty, Integer> {
}