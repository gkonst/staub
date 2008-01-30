package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Conversational;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import ru.spbspu.staub.dao.QuestionDAO;
import ru.spbspu.staub.model.AnswerWrapper;
import ru.spbspu.staub.model.QuestionWrapper;

import java.util.Date;
import java.util.List;


/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("questionDetailBean")
@Scope(ScopeType.CONVERSATION)
@Conversational
public class QuestionDetailBean extends GenericBean {

    @RequestParameter
    private Integer modelId;

    @In
    private QuestionDAO questionDAO;

    @In(required = true)
    private Integer testId;

    private List<Integer> questionIds;
    private QuestionWrapper currentQuestion;
    private int questionIndex = 0;
    private int currentTime = -1;
    private long startTime;

    private AnswerWrapper answer;

    public String initTest() {
        logger.debug(">>> Init test(#0)...", modelId);
        questionIds = questionDAO.findIdsByTestId(modelId);
        currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(questionIndex), false));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());  
        logger.debug(" currentTime : " + currentTime);
        if(currentTime == -1) {
            resetTimer();
        } else {
            refreshTimer();
        }
        logger.debug("<<< Init test...Ok");
        return "questionDetail";
    }

    public String previousQuestion() {
        logger.debug(">>> Previous question...");
        currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(--questionIndex), false));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
        resetTimer();
        logger.debug(">>> Previous question...Ok");
        return null;
    }

    public String nextQuestion() {
        logger.debug(">>> Next question...");
        saveAnswer();
        currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(++questionIndex), false));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
        resetTimer();
        logger.debug(">>> Next question...Ok");
        return null;
    }

    private void resetTimer() {
        currentTime = currentQuestion.getTimeLimit();
        startTime = new Date().getTime();
    }

    private void saveAnswer() {
        logger.debug(" >>> Saving answers...");

        logger.debug(" <<< Saving answers...Ok");
    }

    public void refreshTimer() {
        logger.debug(">>> Refreshing timer...");
        long newTime = (new Date().getTime() - startTime)/1000;
        logger.debug(" time elapsed : " + newTime);
        logger.debug(" time remaining : " + (currentQuestion.getTimeLimit() - (int)newTime));
        this.currentTime = currentQuestion.getTimeLimit() - (int)newTime;
        logger.debug(">>> Refreshing timer...Ok");
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

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
