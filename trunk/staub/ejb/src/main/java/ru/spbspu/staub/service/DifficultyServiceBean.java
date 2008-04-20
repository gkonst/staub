package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Difficulty;

import javax.ejb.Stateless;

/**
 * Stateless EJB Service for manipulations with <code>Difficulty</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("difficultyService")
@AutoCreate
@Stateless
public class DifficultyServiceBean extends GenericServiceBean<Difficulty, Integer> implements DifficultyService {

}