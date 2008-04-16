package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.question.ChoiceType;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>MultipleChoiceAnswerWrapper</code> class is a holder for answer related data. This class is for multiple
 * choice questions answers.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
public class MultipleChoiceAnswerWrapper extends AnswerWrapper {
    private ChoiceType answerDefinition;

    private List<ru.spbspu.staub.model.question.AnswerType> userChoice;

    public MultipleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        userChoice = new ArrayList<ru.spbspu.staub.model.question.AnswerType>();
        this.answerDefinition = answerDefinition;
    }

    public ChoiceType getAnswerDefinition() {
        return answerDefinition;
    }

    public void setAnswerDefinition(ChoiceType answerDefinition) {
        this.answerDefinition = answerDefinition;
    }

    public List<ru.spbspu.staub.model.question.AnswerType> getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(List<ru.spbspu.staub.model.question.AnswerType> userChoice) {
        this.userChoice = userChoice;
    }

    public AnswerType getAnswer() {
        AnswerType answer = null;
        if (!userChoice.isEmpty()) {
            answer = new AnswerType();
            AnswerType.MultipleChoice multipleChoice = new AnswerType.MultipleChoice();
            List<ElementType> elements = multipleChoice.getElement();
            for (ru.spbspu.staub.model.question.AnswerType choice : userChoice) {
                ElementType element = new ElementType();
                element.setAnswerId(choice.getId());
                elements.add(element);
            }
            answer.setMultipleChoice(multipleChoice);
        }
        return answer;
    }
}
