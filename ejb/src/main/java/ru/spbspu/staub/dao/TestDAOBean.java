package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Test;

import javax.ejb.Stateless;

/**
 * Stateless EJB DAO for manipulations with <code>Test</code> entity. 
 *
 * @author Konstantin Grigoriev
 */
@Name("testDAO")
@AutoCreate
@Stateless
public class TestDAOBean extends GenericDAOBean<Test, Integer> implements TestDAO {
}
