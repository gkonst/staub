package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.AnswerType;
import ru.spbspu.staub.model.question.ChoiceType;

import java.math.BigInteger;

/**
 * The <code>ChoiceAnswerWrapper</code> class is a holder for answer related data.
 * This is base class for choice answers (like single or multile choice).
 * Contains common methods for choice aswers.
 *
 * @author Konstantin Grigoriev
 * @param <C> current asnwer class
 */
public abstract class ChoiceAnswerWrapper<C> extends AnswerWrapper<ChoiceType, C> {

    /**
     * Creates new instance of wrapper, not used directly,
     *
     * @param definition specific answer definition
     */
    protected ChoiceAnswerWrapper(ChoiceType definition) {
        super(definition);
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
}
