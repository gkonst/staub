package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.AutoCreate;
import ru.spbspu.staub.entity.Test;

import javax.ejb.Stateless;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("testDAO")
@AutoCreate
@Stateless
public class TestDAOBean extends GenericDAOBean<Test, Integer> implements TestDAO {
}
