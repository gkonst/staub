package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;

/**
 * Local Interface for <code>QuestionServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface QuestionService extends GenericService<Question, Integer> {
    /**
     * Fetches all questions for specific test.
     *
     * @param formProperties form properties
     * @param test           specific test
     * @return result fetch
     */
    FormTable findAllForTest(FormProperties formProperties, Test test);
}
