package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.exception.RemoveException;

import javax.ejb.EJB;
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
    @EJB
    private StudentService studentService;

    @Override
    public void remove(Group group) throws RemoveException {
        logger.debug("> remove(difficulty=#0)", group);

        Group g = getEntityManager().merge(group);
        if ((studentService.countStudents(g)) == 0) {
            logger.debug("*  No related Student entities found.");
            getEntityManager().remove(g);
            logger.debug("*  Group removed from a database.");
        } else {
            logger.debug("*  Related Student entities exist.");
            throw new RemoveException("Could not remove a group.");
        }

        logger.debug("< remove(group=#0)", group);
    }
}