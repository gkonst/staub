package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Group;

import javax.ejb.Stateless;

/**
 * The <code>GroupServiceBean</code> is a stateless EJB service to manipulate <code>Group</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("groupService")
@AutoCreate
@Stateless
public class GroupServiceBean extends GenericServiceBean<Group, Integer> implements GroupService {

}