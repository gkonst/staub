package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Group;

import javax.ejb.Local;

/**
 * The <code>GroupService</code> is a stateless EJB service to manipulate <code>Group</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface GroupService extends GenericService<Group, Integer> {

}