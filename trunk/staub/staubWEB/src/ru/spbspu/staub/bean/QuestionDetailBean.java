package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import ru.spbspu.staub.dao.TestDAO;
import ru.spbspu.staub.entity.Question;

import java.util.Iterator;


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

    private Question currentQuestion;

    private Iterator<Question> questionIterator;

    @Create
    public void startTest() {
        logger.debug(">>> Starting test(#0)...", modelId);
        questionIterator = testDAO.findById(modelId, true).getQuestions().iterator();
        currentQuestion = questionIterator.next();
        logger.debug("<<< Starting test...Ok");
    }

    public void nextQuestion() {
        currentQuestion = questionIterator.next();
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public boolean isHasNextQuestion() {
        return questionIterator.hasNext();    
    }
}
