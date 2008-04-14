package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.entity.UserHistory;

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
        UserHistory userHistory = new UserHistory();
        // TODO: Change username to a real name when an appropriate field will be added to the User entity.
        userHistory.setName(testTrace.getUser().getUsername());
        userHistory.setScore(testTrace.getScore());
        userHistory.setTestDate(testTrace.getFinished());
        userHistory.setTestPassed(testTrace.getTestPassed());
        userHistory.setTestTrace(testTrace);
        makePersistent(userHistory);
    }
}
