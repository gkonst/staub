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
import ru.spbspu.staub.util.TimerModel;

import java.util.List;


/**
 * Main webbean for solving questions.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionBean")
@Scope(ScopeType.CONVERSATION)
@Conversational
public class QuestionBean extends GenericModeBean {
    private static final long serialVersionUID = -8353141534361734709L;

    @In
    private QuestionTraceService questionTraceService;

    @In
    private TestTraceService testTraceService;

    @In(required = true)
    private TestTrace testTrace;

    private List<Integer> questionIds;

    private QuestionTrace currentQuestion;

    private int questionIndex = 0;

    private AnswerWrapper answer;

    private Integer previousPart;

    private boolean finished = false;

    private TimerModel questionTimer;

    private TimerModel testTimer;

    @Override
    public void initBean() {
        if (isBeanModeDefined()) {
            initTest();
        }
    }

    // TODO split method for test initialization and question initialization
    // TODO translate messages
    // TODO part may be wrong sometimes
    private void initTest() {
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
                return;
            }
        }

        if (testTimer == null && testTrace.getTest().getTimeLimit() != null && testTrace.getTest().getTimeLimit() != 0) {
            testTimer = new TimerModel(testTrace.getStarted(), testTrace.getTest().getTimeLimit());
        }

        if (questionIndex < questionIds.size()) {

            fillModel(questionIds.get(questionIndex));

            logger.debug(" currentQuestion : #0", currentQuestion);

            if (previousPart != null && !previousPart.equals(currentQuestion.getPart())) {
                logger.debug(" part changed(#0->#1) -> checking", previousPart, currentQuestion.getPart());
                if (!testTraceService.checkPart(testTrace, previousPart)) {
                    logger.debug(" part checking failed -> test ended");
                    testTrace = testTraceService.endTest(testTrace, false);
                    addFacesMessage("Part validation failed");
                    finished = true;
                    logger.debug("<<< Init question...failed");
                    return;
                }
            }

            // not sure
            if (testTimer != null && testTimer.isExpired()) {
                logger.debug(" test timer expired -> test ended");
                testTraceService.checkPart(testTrace, previousPart != null ? previousPart : currentQuestion.getPart());
                testTrace = testTraceService.endTest(testTrace, false);
                addFacesMessage("время теста истекло");
                finished = true;
                logger.debug("<<< Init question...failed");
                return;
            }

            // starting solving question
            currentQuestion = questionTraceService.start(currentQuestion);

            if (getCurrentQuestion().getTimeLimit() != null && getCurrentQuestion().getTimeLimit() != 0) {
                questionTimer = new TimerModel(currentQuestion.getStarted(), currentQuestion.getQuestion().getTimeLimit());
            } else {
                questionTimer = null;
            }

            if (questionTimer != null && questionTimer.isExpired()) {
                logger.debug(" question timer expired -> next question");
                nextQuestion();
                // recursive =))
                initTest();
                //addFacesMessage("время вопроса истекло"); TODO ?????????
            }

            previousPart = currentQuestion.getPart();
        } else {
            // last question
            logger.debug(" no questions -> test ended");
            boolean result = testTraceService.checkPart(testTrace, previousPart);
            testTrace = testTraceService.endTest(testTrace, result);
            addFacesMessage("Вы ответили на все вопросы теста");
            finished = true;

        }
        logger.debug("<<< Init question...Ok");
    }

    public String nextQuestion() {
        logger.debug(">>> Next question...");
        currentQuestion.setAnswer(answer.getAnswer());
        currentQuestion = questionTraceService.saveAnswer(currentQuestion);
        questionIndex++;
        logger.debug(" question index : #0", questionIndex);
        logger.debug(">>> Next question...Ok");
        return null;
    }

    private void fillModel(Integer modelId) {
        currentQuestion = questionTraceService.findById(modelId);
        answer = AnswerWrapper.getAnswer(currentQuestion.getQuestion().getDefinition());
    }

    public boolean isHasPreviousQuestion() {
        return questionIndex > 0;
    }

    public boolean isHasNextQuestion() {
        return questionIndex < questionIds.size();
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

    public boolean isFinished() {
        return finished;
    }

    public TestTrace getTestTrace() {
        return testTrace;
    }

    public TimerModel getQuestionTimer() {
        return questionTimer;
    }

    public TimerModel getTestTimer() {
        return testTimer;
    }
}
