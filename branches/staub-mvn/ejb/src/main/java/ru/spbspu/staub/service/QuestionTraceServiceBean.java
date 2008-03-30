package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.question.QuestionType;

import javax.ejb.Stateless;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return getEntityManager().createQuery("select qt.id from QuestionTrace qt where qt.testTrace = :testTrace and qt.finished is null")
                .setParameter("testTrace", testTrace).getResultList();
    }

    public void saveAnswer(QuestionTrace questionTrace) {
        getEntityManager().persist(questionTrace);
    }

    public boolean checkGroup(QuestionTrace questionTrace) {
        QuestionType questionType = questionTrace.getQuestion().getQuestion();
        AnswerType answerType = questionTrace.getAnswer();
        boolean result = false;
        if (questionType.getMultipleChoice() != null) {
            result = check(questionType.getMultipleChoice().getAnswer(), answerType.getMultipleChoice().getElement());
        } else if (questionType.getSingleChoice() != null) {
            result = check(questionType.getSingleChoice().getAnswer(), answerType.getSingleChoice().getElement());
        }
        return result;
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
            if (questionAnswer.getCorrect().equals("true")) {
                correctAnswers.add(questionAnswer.getId());
            }
        }
        return correctAnswers;
    }
}
