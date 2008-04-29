package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.question.InputType;
import ru.spbspu.staub.model.question.UserInputType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        map.put(InputType.REAL, "\\s*(-?)\\s*(\\d+)(?:[\\.,](\\d+))?\\s*");
        map.put(InputType.COMPLEX, "\\s*(-?)\\s*(\\d+)(?:[\\.,](\\d+))?\\s*(?:([\\+-])\\s*(\\d+)(?:[\\.,](\\d+))?\\s*\\*?\\s*[ij]\\s*)?");
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
        LOG.debug("> resolveCorrectAnswer()");

        String answer = getCurrent();
        getDefinition().setAnswer(answer);

        StringBuilder regexp = new StringBuilder();
        if (isInteger()) {
            LOG.debug("*  Regexp for integer.");
            Pattern pattern = Pattern.compile(REGEX_MAP.get(InputType.INTEGER));
            Matcher matcher = pattern.matcher(answer);
            if (matcher.matches()) { // TODO: Throw an exception if false?
                regexp.append("\\s*");
                String minus = matcher.group(1);
                if ((minus != null) && (minus.length() > 0)) {
                    regexp.append("-\\s*");
                }
                regexp.append(matcher.group(2));
                regexp.append("\\s*");
            }
        } else if (isReal()) {
            LOG.debug("*  Regexp for real.");
            Pattern pattern = Pattern.compile(REGEX_MAP.get(InputType.REAL));
            Matcher matcher = pattern.matcher(answer);
            if (matcher.matches()) {
                regexp.append("\\s*");
                String minus = matcher.group(1);
                if ((minus != null) && (minus.length() > 0)) {
                    regexp.append("-\\s*");
                }
                regexp.append(matcher.group(2));
                String fraction = matcher.group(3);
                if ((fraction != null) && (fraction.length() > 0)) {
                    regexp.append("[\\.,]").append(fraction);
                }
                regexp.append("\\s*");
            }
        } else if (isComplex()) {
            LOG.debug("*  Regexp for complex.");
            Pattern pattern = Pattern.compile(REGEX_MAP.get(InputType.COMPLEX));
            Matcher matcher = pattern.matcher(answer);
            if (matcher.matches()) {
                regexp.append("\\s*");
                String minus = matcher.group(1);
                if ((minus != null) && (minus.length() > 0)) {
                    regexp.append("-\\s*");
                }
                regexp.append(matcher.group(2));
                String fraction = matcher.group(3);
                if ((fraction != null) && (fraction.length() > 0)) {
                    regexp.append("[\\.,]").append(fraction);
                }
                regexp.append("\\s*");
                String sign = matcher.group(4);
                if ((sign != null) && (sign.length() > 0)) {
                    if ("+".equals(sign)) {
                        regexp.append("\\+");
                    } else {
                        regexp.append("-");
                    }
                    regexp.append("\\s*");
                    regexp.append(matcher.group(5));
                    String imaginaryFraction = matcher.group(6);
                    if ((imaginaryFraction != null) && (imaginaryFraction.length() > 0)) {
                        regexp.append("[\\.,]").append(imaginaryFraction);
                    }
                    regexp.append("\\s*\\*?\\s*[ij]\\s*");
                }
            }
        } else if (isString()) {
            LOG.debug("*  Answer is a string. Regexp construction skipped.");
        }

        String regexpString = regexp.toString();
        getDefinition().setRegexp(regexpString);
        LOG.debug("*  Regexp : ", regexpString);

        LOG.debug("< resolveCorrectAnswer()");
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
