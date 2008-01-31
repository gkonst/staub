package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Conversational;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
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
        logger.debug(">>> Init test(#0)...", testId);
        questionIds = questionDAO.findIdsByTestId(testId);
        currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(questionIndex), false));
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());  
        logger.debug(" currentTime : #0", currentTime);
        if(currentTime == -1) {
            resetTimer();
        } else {
            refreshTimer();
        }
        logger.debug("<<< Init test...Ok");
        return "questionDetail";
    }

    @End
    public String endTest() {
        logger.debug(">>> End test...");
        logger.debug(">>> End test...Ok");
        return "testEnd";
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
        questionIndex++;
        logger.debug(" question index : #0", questionIndex);
        if(questionIndex < questionIds.size()) {
            currentQuestion = new QuestionWrapper(questionDAO.findById(questionIds.get(questionIndex), false));
            answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition());
            resetTimer();
        }
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
        return questionIndex < questionIds.size();
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
