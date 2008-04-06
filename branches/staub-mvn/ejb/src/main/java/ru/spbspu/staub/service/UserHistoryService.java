package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.UserHistory;
import ru.spbspu.staub.entity.TestTrace;

import javax.ejb.Local;

/**
 * The <code>UserHistoryService</code> interface is a local interface for {@link UserHistoryServiceBean} EJB.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface UserHistoryService extends GenericService<UserHistory, Integer> {
    void populateUserHistory(TestTrace testTrace);
}
