package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.model.question.UserInputType;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (questionTrace.getStarted() == null) {
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
        if (answerType != null) {
            if (questionType.getMultipleChoice() != null) {
                result = check(questionType.getMultipleChoice().getAnswer(),
                        answerType.getMultipleChoice().getElement());
            } else if (questionType.getSingleChoice() != null) {
                result = check(questionType.getSingleChoice().getAnswer(), answerType.getSingleChoice().getElement());
            } else if (questionType.getUserInput() != null) {
                result = check(questionType.getUserInput(), answerType.getUserInput());
            }
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

    private boolean check(UserInputType userInputType, String userAnswer) {
        String regexp = userInputType.getRegexp();
        if ((regexp == null) || (regexp.length() == 0)) {
            return (userAnswer == null) || (userAnswer.length() == 0);
        } else {
            Pattern p = Pattern.compile(regexp);
            Matcher m = p.matcher(userAnswer);
            return m.matches();
        }
    }


    private Set<BigInteger> getCorrectAnswers
            (List<ru.spbspu.staub.model.question.AnswerType> questionAnswers) {
        Set<BigInteger> correctAnswers = new HashSet<BigInteger>();
        for (ru.spbspu.staub.model.question.AnswerType questionAnswer : questionAnswers) {
            if ("true".equals(questionAnswer.getCorrect())) {
                correctAnswers.add(questionAnswer.getId());
            }
        }
        return correctAnswers;
    }
}
