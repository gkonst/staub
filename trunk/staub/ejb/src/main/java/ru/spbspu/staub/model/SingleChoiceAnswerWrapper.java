package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.question.ChoiceType;

import java.math.BigInteger;

/**
 * The <code>SingleChoiceAnswerWrapper</code> class is a holder for answer related data. This class is for single choice
 * questions answers.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
public class SingleChoiceAnswerWrapper extends ChoiceAnswerWrapper {

    protected SingleChoiceAnswerWrapper(ChoiceType definition) {
        super(definition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return Type.SINGLE_CHOICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnswerType getAnswer() {
        AnswerType answer = null;
        if (!isCurrentEmpty()) {
            answer = new AnswerType();
            AnswerType.SingleChoice singleChoice = new AnswerType.SingleChoice();
            for (BigInteger choiceId : getCurrent().keySet()) {
                if (getCurrent().get(choiceId)) {
                    ElementType element = new ElementType();
                    element.setAnswerId(choiceId);
                    singleChoice.setElement(element);
                }
            }
            answer.setSingleChoice(singleChoice);
        }
        return answer;
    }
}
