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
public class SingleChoiceAnswerWrapper extends ChoiceAnswerWrapper<ru.spbspu.staub.model.question.AnswerType> {

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
    public void resolveCorrectAnswer() {
        for (ru.spbspu.staub.model.question.AnswerType type : getDefinition().getAnswer()) {
            if (getCurrent().getId().equals(type.getId())) {
                type.setCorrect(String.valueOf(Boolean.TRUE));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void determineCorrectAnswer() {
        for (ru.spbspu.staub.model.question.AnswerType type : getDefinition().getAnswer()) {
            if (Boolean.valueOf(type.getCorrect())) {
                setCurrent(type);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ru.spbspu.staub.model.answer.AnswerType getAnswer() {
        ru.spbspu.staub.model.answer.AnswerType answer = null;
        if (getCurrent() != null) {
            answer = new ru.spbspu.staub.model.answer.AnswerType();
            AnswerType.SingleChoice singleChoice = new AnswerType.SingleChoice();
            ElementType element = new ElementType();
            element.setAnswerId(getCurrent().getId());
            singleChoice.setElement(element);
            answer.setSingleChoice(singleChoice);
        }
        return answer;
    }
}
