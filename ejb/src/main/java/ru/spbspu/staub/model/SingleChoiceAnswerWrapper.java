package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.question.ChoiceType;

/**
 * The <code>SingleChoiceAnswerWrapper</code> class is a holder for answer related data. This class is for single choice
 * questions answers.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
public class SingleChoiceAnswerWrapper extends AnswerWrapper {
    private ChoiceType answerDefinition;

    private ru.spbspu.staub.model.question.AnswerType userChoice;

    public SingleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        this.answerDefinition = answerDefinition;
    }

    public ru.spbspu.staub.model.question.AnswerType getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(ru.spbspu.staub.model.question.AnswerType userChoice) {
        this.userChoice = userChoice;
    }

    public ChoiceType getAnswerDefinition() {
        return answerDefinition;
    }

    public void setAnswerDefinition(ChoiceType answerDefinition) {
        this.answerDefinition = answerDefinition;
    }

    public ru.spbspu.staub.model.answer.AnswerType getAnswer() {
        ru.spbspu.staub.model.answer.AnswerType answer = null;
        if (userChoice != null) {
            answer = new ru.spbspu.staub.model.answer.AnswerType();
            AnswerType.SingleChoice singleChoice = new AnswerType.SingleChoice();
            ElementType element = new ElementType();
            element.setAnswerId(userChoice.getId());
            singleChoice.setElement(element);
            answer.setSingleChoice(singleChoice);
        }
        return answer;
    }
}
