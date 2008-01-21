package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import ru.spbspu.staub.dao.QuestionDAO;
import ru.spbspu.staub.model.AnswerWrapper;
import ru.spbspu.staub.model.QuestionWrapper;

import java.util.List;


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
    private QuestionDAO questionDAO;

    private List<Integer> questionIds;
    private QuestionWrapper currentQuestion;
    private int questionIndex = 0;

    private AnswerWrapper answer;

    @Create
    public void startTest() {
        logger.debug(">>> Starting test(#0)...", modelId);
        questionIds = questionDAO.findIdsByTestId(modelId);
        currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(questionIndex), false));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
        logger.debug("<<< Starting test...Ok");
    }

    public void previousQuestion() {
        currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(--questionIndex), false));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
    }

    public void nextQuestion() {
        saveAnswer();
        currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(++questionIndex), false));
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
        return (questionIndex + 1) < questionIds.size();
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
