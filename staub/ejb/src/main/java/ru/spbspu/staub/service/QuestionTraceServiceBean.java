package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.question.InputType;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.model.question.UserInputType;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;
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
        Query q = getEntityManager().createQuery("select qt.id from QuestionTrace qt where qt.testTrace = :testTrace and qt.finished is null order by qt.part");
        q.setParameter("testTrace", testTrace);
        return q.getResultList();
    }

    public FormTable find(FormProperties formProperties, TestTrace testTrace) {
        logger.debug("> find(FormProperties=#0, TestTrace=#1)", formProperties, testTrace);

        String query = "select qt from TestTrace tt join tt.questionTraces qt where tt = :testTrace order by qt.part";

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("testTrace", testTrace);

        FormTable formTable = findAll(query, formProperties, parameters);

        logger.debug("< find(FormProperties, TestTrace)");

        return formTable;
    }

    public long count(Question question) {
        Query q = getEntityManager().createQuery("select count(q) from QuestionTrace q where q.question = :question");
        q.setParameter("question", question);
        return (Long) q.getSingleResult();
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
        Date finished = new Date();
        questionTrace.setFinished(finished);
        Date started = questionTrace.getStarted();
        questionTrace.setTotalTime((int) (finished.getTime() - started.getTime()) / 1000);

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
            if (InputType.STRING == userInputType.getType()) {
                String answer = userInputType.getAnswer();
                if ((answer != null) && (answer.length() > 0)) {
                    return answer.equals(userAnswer);
                } else {
                    return ((userAnswer == null) || (userAnswer.length() == 0));
                }
            } else { // Should not happen.
                return false;
            }
        } else {
            Pattern p = Pattern.compile(regexp);
            Matcher m = p.matcher(userAnswer);
            return m.matches();
        }
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
