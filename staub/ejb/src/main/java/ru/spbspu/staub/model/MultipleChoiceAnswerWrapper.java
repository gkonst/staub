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
public class MultipleChoiceAnswerWrapper extends ChoiceAnswerWrapper<List<ru.spbspu.staub.model.question.AnswerType>> {

    protected MultipleChoiceAnswerWrapper(ChoiceType definition) {
        super(definition);
        setCurrent(new ArrayList<ru.spbspu.staub.model.question.AnswerType>());
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
    public void resolveCorrectAnswer() {
        for (ru.spbspu.staub.model.question.AnswerType type : getDefinition().getAnswer()) {
            type.setCorrect(String.valueOf(Boolean.FALSE));
            for (ru.spbspu.staub.model.question.AnswerType correctAnswer : getCurrent()) {
                if (correctAnswer.getId().equals(type.getId())) {
                    type.setCorrect(String.valueOf(Boolean.TRUE));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void determineCorrectAnswer() {
        setCurrent(new ArrayList<ru.spbspu.staub.model.question.AnswerType>()); // redundant
        for (ru.spbspu.staub.model.question.AnswerType type : getDefinition().getAnswer()) {
            if (Boolean.valueOf(type.getCorrect())) {
                getCurrent().add(type);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnswerType getAnswer() {
        AnswerType answer = null;
        if (!getCurrent().isEmpty()) {
            answer = new AnswerType();
            AnswerType.MultipleChoice multipleChoice = new AnswerType.MultipleChoice();
            List<ElementType> elements = multipleChoice.getElement();
            for (ru.spbspu.staub.model.question.AnswerType choice : getCurrent()) {
                ElementType element = new ElementType();
                element.setAnswerId(choice.getId());
                elements.add(element);
            }
            answer.setMultipleChoice(multipleChoice);
        }
        return answer;
    }
}
