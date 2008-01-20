package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import ru.spbspu.staub.dao.TestDAO;
import ru.spbspu.staub.dao.QuestionDAO;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.QuestionWrapper;
import ru.spbspu.staub.model.AnswerWrapper;


/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("questionDetailBean")
@Scope(ScopeType.CONVERSATION)
public class QuestionDetailBean extends GenericBean {

    @RequestParameter
    private Integer modelId;

    @In
    private TestDAO testDAO;

    @In
    private QuestionDAO questionDAO;

    private Test test;
    private QuestionWrapper currentQuestion;
    private int questionIndex = 0;

    private AnswerWrapper answer;

    @Create
    public void startTest() {
        logger.debug(">>> Starting test(#0)...", modelId);
        test = testDAO.findById(modelId, true);
        questionDAO.findIdsByTestId(test.getId());
        currentQuestion = new QuestionWrapper(test.getQuestions().get(questionIndex));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
        logger.debug("<<< Starting test...Ok");
    }

    public void previousQuestion() {
        currentQuestion = new QuestionWrapper(test.getQuestions().get(--questionIndex));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
    }

    public void nextQuestion() {
        saveAnswer();
        currentQuestion = new QuestionWrapper(test.getQuestions().get(++questionIndex));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
    }

    private void saveAnswer() {
        logger.debug(">>> Saving answers...");

        logger.debug("<<< Saving answers...");
    }

    public boolean isHasPreviousQuestion() {
        return questionIndex > 0;
    }

    public boolean isHasNextQuestion() {
        return (questionIndex + 1) < test.getQuestions().size();
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public QuestionWrapper getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(QuestionWrapper currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public AnswerWrapper getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerWrapper answer) {
        this.answer = answer;
    }
}
