package ru.spbspu.staub.bean.question;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Conversational;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericModeBean;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.AnswerWrapper;
import ru.spbspu.staub.service.QuestionTraceService;
import ru.spbspu.staub.service.TestTraceService;

import java.util.Date;
import java.util.List;


/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("questionBean")
@Scope(ScopeType.CONVERSATION)
@Conversational
public class QuestionBean extends GenericModeBean {

    @In
    private QuestionTraceService questionTraceService;

    @In
    private TestTraceService testTraceService;

    @In(required = true)
    private TestTrace testTrace;

    private List<Integer> questionIds;
    private QuestionTrace currentQuestion;
    private int questionIndex = 0;
    private int currentTime = -1;
    // TODO remove
    private long startTime;

    private AnswerWrapper answer;

    private Integer previousPart;

    private boolean finished = false;

    public void initBean() {
        if (isBeanModeDefined()) {
            initTest();
        }
    }

    public String initTest() {
        // TODO remove return
        logger.debug(">>> Init question(index=#0) in testTrace(#1)...", questionIndex, testTrace.getId());
        logger.debug(" this bean : #0", this);
        // fill ids
        if (questionIds == null) {
            questionIds = questionTraceService.findIdsByTestTraceId(testTrace);
            if (questionIds == null || questionIds.size() == 0) {
                // check for zero count questions
                addFacesMessage("В тесте не осталось неотвеченных вопросов");
                finished = true;
                logger.debug("<<< Init question...failed");
                return "questionDetail";
            }
        }

        if (questionIndex < questionIds.size()) {

            fillModel(questionIds.get(questionIndex));

            logger.debug(" currentQuestion : #0", currentQuestion);
            if (isTimeLimitPresent()) {
                logger.debug(" currentTime : #0", currentTime);
                if (currentTime == -1) {
                    resetTimer();
                } else {
                    refreshTimer();
                }
            }

            if (previousPart != null && !previousPart.equals(currentQuestion.getPart())) {
                logger.debug(" part changed(#0->#1) -> checking", previousPart, currentQuestion.getPart());
                if (!testTraceService.checkPart(testTrace, previousPart, 100)) {
                    logger.debug(" part checking failed -> test ended");
                    testTrace = testTraceService.endTest(testTrace);
                    addFacesMessage("Part validation failed");
                    finished = true;
                }
            }

            // starting solving question
            currentQuestion = questionTraceService.start(currentQuestion);

            previousPart = currentQuestion.getPart();
        } else {
            // last question
            logger.debug(" no questions -> test ended");
            testTraceService.checkPart(testTrace, previousPart, testTrace.getTest().getPassScore());
            testTrace = testTraceService.endTest(testTrace);
            addFacesMessage("Вы ответили на все вопросы теста");
            finished = true;

        }
        logger.debug("<<< Init question...Ok");
        return "questionDetail";
    }

    // unused
    public String previousQuestion() {
        logger.debug(">>> Previous question...");
        fillModel(questionIds.get(--questionIndex));
        resetTimer();
        logger.debug(">>> Previous question...Ok");
        return null;
    }

    public String nextQuestion() {
        logger.debug(">>> Next question...");
        currentQuestion.setAnswer(answer.getAnswer());
        currentQuestion = questionTraceService.saveAnswer(currentQuestion);
        questionIndex++;
        currentTime = -1;
        logger.debug(" question index : #0", questionIndex);
        logger.debug(">>> Next question...Ok");
        return null;
    }

    private void fillModel(Integer modelId) {
        currentQuestion = questionTraceService.findById(modelId);
        answer = AnswerWrapper.getAnswer(currentQuestion.getQuestion().getDefinition());
    }

    private void resetTimer() {
        currentTime = getCurrentQuestion().getTimeLimit();
        startTime = new Date().getTime();
    }

    public void refreshTimer() {
        logger.debug(">>> Refreshing timer...");
        long newTime = (new Date().getTime() - startTime) / 1000;
        logger.debug(" time elapsed : " + newTime);
        logger.debug(" time remaining : " + (getCurrentQuestion().getTimeLimit() - (int) newTime));
        this.currentTime = getCurrentQuestion().getTimeLimit() - (int) newTime;
        logger.debug(">>> Refreshing timer...Ok");
    }

    public boolean isHasPreviousQuestion() {
        return questionIndex > 0;
    }

    public boolean isHasNextQuestion() {
        return questionIndex < questionIds.size();
    }

    public boolean isTimeLimitPresent() {
        return getCurrentQuestion().getTimeLimit() != null && getCurrentQuestion().getTimeLimit() != 0;
    }

    public Question getCurrentQuestion() {
        return currentQuestion.getQuestion();
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

    public boolean isFinished() {
        return finished;
    }

    public TestTrace getTestTrace() {
        return testTrace;
    }
}
