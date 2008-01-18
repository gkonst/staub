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
import ru.spbspu.staub.model.question.AnswerType;
import ru.spbspu.staub.model.question.QuestionType;

import javax.faces.model.SelectItem;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.ArrayList;
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
    private TestDAO testDAO;

    private Test test;
    private Question currentQuestion;
    private QuestionType questionDefinition;
    private int questionIndex = 0;

    private Unmarshaller unmarshaller = null;

    private String[] answerMultipleChoice = null;
    private List<SelectItem> answerChoiceItems = null;

    @Create
    public void startTest() {
        logger.debug(">>> Starting test(#0)...", modelId);
        test = testDAO.findById(modelId, true);
        currentQuestion = test.getQuestions().get(questionIndex);
        questionDefinition = parseQuestionDefinationXML(currentQuestion.getDefinition());
        logger.debug("<<< Starting test...Ok");
    }

    public void previousQuestion() {
        currentQuestion = test.getQuestions().get(--questionIndex);
        questionDefinition = parseQuestionDefinationXML(currentQuestion.getDefinition());
    }

    public void nextQuestion() {
        saveAnswer();
        currentQuestion = test.getQuestions().get(++questionIndex);
        questionDefinition = parseQuestionDefinationXML(currentQuestion.getDefinition());
    }

    private QuestionType parseQuestionDefinationXML(String questionDefinationXML) {
        try {
            logger.debug(" Parsing question defination XML...");
            logger.debug("  XML : " + questionDefinationXML);
            if (unmarshaller == null) {
                JAXBContext jaxbContext = JAXBContext.newInstance("ru.spbspu.staub.model.question");
                unmarshaller = jaxbContext.createUnmarshaller();
            }
            JAXBElement jaxbElement = (JAXBElement) unmarshaller.unmarshal(new StreamSource(new StringReader(questionDefinationXML)));
            QuestionType result = (QuestionType) jaxbElement.getValue();
            logger.debug("  description : " + result.getDescription());
            logger.debug("  multipleChoice : " + result.getMultipleChoice());
            logger.debug("  singleChoice : " + result.getSingleChoice());
            if(result.getMultipleChoice() != null) {
                answerChoiceItems = new ArrayList<SelectItem>();
                for(AnswerType choice : result.getMultipleChoice().getAnswer()) {
                    logger.debug("    choice answer(correct=" + choice.getCorrect() + ", value=" + choice.getValue() + ")");
                    answerChoiceItems.add(new SelectItem(String.valueOf(choice.getCorrect()), choice.getValue()));
                }
                answerMultipleChoice = new String[answerChoiceItems.size()];
            } else if(result.getSingleChoice() != null) {
                answerChoiceItems = new ArrayList<SelectItem>();
                for(AnswerType choice : result.getSingleChoice().getAnswer()) {
                    answerChoiceItems.add(new SelectItem(choice.getCorrect(), choice.getValue()));
                }
            }

            logger.debug(" Parsing question defination XML...Ok");
            return result;
        } catch (JAXBException e) {
            logger.error("Error during parsing question defination XML(" + questionDefinationXML + ")", e);
            throw new RuntimeException(e);
        }
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

    public boolean isMultipleChoiceAnswer() {
        return questionDefinition.getMultipleChoice() != null;
    }

    public List<SelectItem> getAnswerChoiceItems() {
        return answerChoiceItems;
    }

    public void setAnswerChoiceItems(List<SelectItem> answerChoiceItems) {
        this.answerChoiceItems = answerChoiceItems;
    }

    public String[] getAnswerMultipleChoice() {
        return answerMultipleChoice;
    }

    public void setAnswerMultipleChoice(String[] answerMultipleChoice) {
        this.answerMultipleChoice = answerMultipleChoice;
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

    public QuestionType getQuestionDefinition() {
        return questionDefinition;
    }

    public void setQuestionDefinition(QuestionType questionDefinition) {
        this.questionDefinition = questionDefinition;
    }
}
