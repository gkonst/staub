package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.question.InputType;
import ru.spbspu.staub.model.question.UserInputType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>UserInputAnswerWrapper</code> class is a holder for answer related data. This class is for user input
 * questions answers.
 *
 * @author Alexander V. Elagin
 */
public class UserInputAnswerWrapper extends AnswerWrapper<UserInputType, String> {

    public static final Map<InputType, String> REGEX_MAP;

    static {
        HashMap<InputType, String> map = new HashMap<InputType, String>();
        map.put(InputType.INTEGER, "\\s*(-?)\\s*(\\d+)\\s*");
        map.put(InputType.REAL, "\\s*(-?)\\s*(\\d+)(?:[.,](\\d+))?\\s*");
        map.put(InputType.COMPLEX, "\\s*(-?)\\s*(\\d+)(?:[.,](\\d+))?\\s*(?:([\\+-])\\s*(\\d+)(?:[.,](\\d+))?(?:\\s*\\*?\\s*[ij])?\\s*)?");
        REGEX_MAP = Collections.unmodifiableMap(map);
    }


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
        //TODO convert to regular expression.
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

    public boolean isComplex() {
        return InputType.COMPLEX.equals(getDefinition().getType());
    }

    public boolean isReal() {
        return InputType.REAL.equals(getDefinition().getType());
    }

    public boolean isInteger() {
        return InputType.INTEGER.equals(getDefinition().getType());
    }

    public boolean isString() {
        return InputType.STRING.equals(getDefinition().getType());
    }
}
