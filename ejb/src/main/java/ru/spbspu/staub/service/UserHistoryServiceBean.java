package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.UserHistory;
import ru.spbspu.staub.entity.TestTrace;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.AutoCreate;

import javax.ejb.Stateless;

/**
 * The <code>UserHistoryServiceBean</code> is a stateless EJB service to manipulate <code>UserHistory</code> entity.
 *
 * @author Alexander V. Elagin
 */
@Name("userHistoryService")
@AutoCreate
@Stateless
public class UserHistoryServiceBean extends GenericServiceBean<UserHistory, Integer> implements UserHistoryService {
    public void populateUserHistory(TestTrace testTrace) {
        // TODO: Implement this method.
    }
}
