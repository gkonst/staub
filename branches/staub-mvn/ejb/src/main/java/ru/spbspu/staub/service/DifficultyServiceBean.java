package ru.spbspu.staub.service;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.AutoCreate;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.interceptors.CallLogger;

/**
 * Stateless EJB Service for manipulations with <code>Difficulty</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("difficultyService")
@AutoCreate
@Interceptors({CallLogger.class})
@Stateless
public class DifficultyServiceBean extends GenericServiceBean<Difficulty, Integer> implements DifficultyService {
}