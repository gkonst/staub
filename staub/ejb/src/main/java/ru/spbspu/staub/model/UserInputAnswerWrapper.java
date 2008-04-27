package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.question.InputType;
import ru.spbspu.staub.model.question.UserInputType;

/**
 * The <code>UserInputAnswerWrapper</code> class is a holder for answer related data. This class is for user input
 * questions answers.
 *
 * @author Alexander V. Elagin
 */
public class UserInputAnswerWrapper extends AnswerWrapper<UserInputType, String> {

    protected UserInputAnswerWrapper(UserInputType definition) {
        super(definition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return Type.USER_INPUT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resolveCorrectAnswer() {
        getDefinition().setAnswer(getCurrent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void determineCorrectAnswer() {
        setCurrent(getDefinition().getAnswer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnswerType getAnswer() {
        AnswerType answerType = new AnswerType();
        answerType.setUserInput(getCurrent());
        return answerType;
    }

    public InputType[] getSubtypes() {
        return InputType.values();
    }
}
