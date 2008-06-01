package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.AnswerType;
import ru.spbspu.staub.model.question.ChoiceType;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>ChoiceAnswerWrapper</code> class is a holder for answer related data.
 * This is base class for choice answers (like single or multile choice).
 * Contains common methods for choice aswers.
 *
 * @author Konstantin Grigoriev
 */
public abstract class ChoiceAnswerWrapper extends AnswerWrapper<ChoiceType, Map<BigInteger, Boolean>> {

    /**
     * Creates new instance of wrapper, not used directly,
     *
     * @param definition specific answer definition
     */
    protected ChoiceAnswerWrapper(ChoiceType definition) {
        super(definition);
        setCurrent(new HashMap<BigInteger, Boolean>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resolveCorrectAnswer() {
        for (ru.spbspu.staub.model.question.AnswerType type : getDefinition().getAnswer()) {
            type.setCorrect(String.valueOf(getCurrent().get(type.getId())));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void determineCorrectAnswer() {
        for (ru.spbspu.staub.model.question.AnswerType type : getDefinition().getAnswer()) {
            getCurrent().put(type.getId(), Boolean.valueOf(type.getCorrect()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        return !isCurrentEmpty();
    }

    /**
     * Adds new answer variant to choice answer.
     */
    public void addAnswer() {
        AnswerType newAnswer = new AnswerType();
        int newAnswerId = getDefinition().getAnswer().size() + 1;
        newAnswer.setId(new BigInteger(String.valueOf(newAnswerId)));
        newAnswer.setValue(String.valueOf(newAnswerId));
        getDefinition().getAnswer().add(newAnswer);
    }

    /**
     * Removes answer variant from choice answer.
     */
    public void removeAnswer() {
        if (!getDefinition().getAnswer().isEmpty()) {
            int answerToRemoveId = getDefinition().getAnswer().size() - 1;
            getDefinition().getAnswer().remove(answerToRemoveId);
        }
    }

    protected boolean isCurrentEmpty() {
        return !getCurrent().containsValue(Boolean.TRUE);
    }
}
