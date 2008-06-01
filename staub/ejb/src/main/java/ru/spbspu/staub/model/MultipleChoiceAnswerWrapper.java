package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.answer.ElementType;
import ru.spbspu.staub.model.question.ChoiceType;

import java.math.BigInteger;
import java.util.List;

/**
 * The <code>MultipleChoiceAnswerWrapper</code> class is a holder for answer related data. This class is for multiple
 * choice questions answers.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
public class MultipleChoiceAnswerWrapper extends ChoiceAnswerWrapper {

    protected MultipleChoiceAnswerWrapper(ChoiceType definition) {
        super(definition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return Type.MULTIPLE_CHOICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnswerType getAnswer() {
        AnswerType answer = null;
        if (!isCurrentEmpty()) {
            answer = new AnswerType();
            AnswerType.MultipleChoice multipleChoice = new AnswerType.MultipleChoice();
            List<ElementType> elements = multipleChoice.getElement();
            for (BigInteger choiceId : getCurrent().keySet()) {
                if (getCurrent().get(choiceId)) {
                    ElementType element = new ElementType();
                    element.setAnswerId(choiceId);
                    elements.add(element);
                }
            }
            answer.setMultipleChoice(multipleChoice);
        }
        return answer;
    }
}
