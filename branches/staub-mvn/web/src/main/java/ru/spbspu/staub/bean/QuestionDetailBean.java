package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.model.AnswerWrapper;
import ru.spbspu.staub.model.QuestionWrapper;
import ru.spbspu.staub.service.QuestionTraceService;

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
    private QuestionTraceService questionTraceService;

    @In(required = true)
    private Integer testTraceId;

    private List<Integer> questionIds;
    private QuestionWrapper currentQuestion;
    private int questionIndex = 0;
    private int currentTime = -1;
    private long startTime;

    private AnswerWrapper answer;

    public String initTest() {
        logger.debug(">>> Init test(#0)...", testTraceId);
        questionIds = questionTraceService.findIdsByTestTraceId(testTraceId);
        fillModel(questionIds.get(questionIndex));
        logger.debug(" currentTime : #0", currentTime);
        if (currentTime == -1) {
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
        fillModel(questionIds.get(--questionIndex));
        resetTimer();
        logger.debug(">>> Previous question...Ok");
        return null;
    }

    public String nextQuestion() {
        logger.debug(">>> Next question...");
        saveAnswer();
        questionIndex++;
        logger.debug(" question index : #0", questionIndex);
        if (questionIndex < questionIds.size()) {
            fillModel(questionIds.get(questionIndex));
            resetTimer();
        }
        logger.debug(">>> Next question...Ok");
        return null;
    }

    private void fillModel(Integer modelId) {
        QuestionTrace questionTrace = questionTraceService.findById(modelId);
        currentQuestion = new QuestionWrapper(questionTrace.getQuestion());
        answer = AnswerWrapper.getAnswer(currentQuestion.getDefinition(), questionTrace);
    }

    private void resetTimer() {
        currentTime = currentQuestion.getTimeLimit();
        startTime = new Date().getTime();
    }

    private void saveAnswer() {
        logger.debug(" >>> Saving answers...");
        //questionTraceService.saveAnswer(answer.unwrap());
        logger.debug(" <<< Saving answers...Ok");
    }

    public void refreshTimer() {
        logger.debug(">>> Refreshing timer...");
        long newTime = (new Date().getTime() - startTime) / 1000;
        logger.debug(" time elapsed : " + newTime);
        logger.debug(" time remaining : " + (currentQuestion.getTimeLimit() - (int) newTime));
        this.currentTime = currentQuestion.getTimeLimit() - (int) newTime;
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
