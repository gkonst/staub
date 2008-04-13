package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.question.QuestionType;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;

/**
 * Stateless EJB Service for manipulations with <code>QuestionTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionTraceService")
@AutoCreate
@Stateless
public class QuestionTraceServiceBean extends GenericServiceBean<QuestionTrace, Integer> implements QuestionTraceService {
    @SuppressWarnings("unchecked")
    public List<Integer> findIdsByTestTraceId(TestTrace testTrace) {
        Query q = getEntityManager().createQuery("select qt.id from QuestionTrace qt where qt.testTrace = :testTrace and qt.finished is null");
        q.setParameter("testTrace", testTrace);
        return q.getResultList();
    }

    public QuestionTrace start(QuestionTrace questionTrace) {
        if (questionTrace.getFinished() == null) {
            questionTrace.setStarted(new Date());
            return makePersistent(questionTrace);
        } else {
            return questionTrace;
        }
    }

    public QuestionTrace saveAnswer(QuestionTrace questionTrace) {
        questionTrace.setFinished(new Date());
        return makePersistent(questionTrace);
    }

    public QuestionTrace checkQuestion(QuestionTrace questionTrace) {
        QuestionType questionType = questionTrace.getQuestion().getDefinition();
        AnswerType answerType = questionTrace.getAnswer();
        boolean result = false;
        if (questionType.getMultipleChoice() != null) {
            result = check(questionType.getMultipleChoice().getAnswer(), answerType.getMultipleChoice().getElement());
        } else if (questionType.getSingleChoice() != null) {
            result = check(questionType.getSingleChoice().getAnswer(), answerType.getSingleChoice().getElement());
        }

        questionTrace.setCorrect(result);

        makePersistent(questionTrace);

        return questionTrace;
    }

    private boolean check(List<ru.spbspu.staub.model.question.AnswerType> questionAnswers,
                          List<ElementType> userAnswers) {
        Set<BigInteger> correctAnswers = getCorrectAnswers(questionAnswers);
        if (userAnswers.size() == correctAnswers.size()) { // redundant check
            for (ElementType userAnswer : userAnswers) {
                if (!correctAnswers.contains(userAnswer.getAnswerId())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean check(List<ru.spbspu.staub.model.question.AnswerType> questionAnswers, ElementType userAnswer) {
        Set<BigInteger> correctAnswers = getCorrectAnswers(questionAnswers);
        return correctAnswers.contains(userAnswer.getAnswerId());
    }

    private Set<BigInteger> getCorrectAnswers(List<ru.spbspu.staub.model.question.AnswerType> questionAnswers) {
        Set<BigInteger> correctAnswers = new HashSet<BigInteger>();
        for (ru.spbspu.staub.model.question.AnswerType questionAnswer : questionAnswers) {
            if ("true".equals(questionAnswer.getCorrect())) {
                correctAnswers.add(questionAnswer.getId());
            }
        }
        return correctAnswers;
    }
}
