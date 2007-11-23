package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import ru.spbspu.staub.dao.TestDAO;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;


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

    private Test test;
    private Question currentQuestion;
    private int questionIndex = 0;

    @Create
    public void startTest() {
        logger.debug(">>> Starting test(#0)...", modelId);
        test = testDAO.findById(modelId, true);
        currentQuestion = test.getQuestions().get(questionIndex);
        logger.debug("<<< Starting test...Ok");
    }

     public void previousQuestion() {
        currentQuestion = test.getQuestions().get(--questionIndex);
    }

    public void nextQuestion() {
        currentQuestion = test.getQuestions().get(++questionIndex);
    }

    public boolean isHasPreviousQuestion() {
        return questionIndex > 0;
    }

    public boolean isHasNextQuestion() {
        return (questionIndex + 1) < test.getQuestions().size();
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
