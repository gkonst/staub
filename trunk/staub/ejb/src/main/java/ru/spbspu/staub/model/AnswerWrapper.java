package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.ChoiceType;
import ru.spbspu.staub.model.question.InputType;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.model.question.UserInputType;

/**
 * The <code>AnswerWrapper</code> class is a holder for answer related data.
 * This is base class for all wrappers with base methods definations, also
 * it's factory class for all subclasses.
 *
 * @author Konstantin Grigoriev
 * @param <D> answer definition class
 * @param <C> current answer class
 */
public abstract class AnswerWrapper<D, C> {

    /**
     * Answers definition, as it is.
     */
    private D definition;
    /**
     * Current answer value, as selected on UI.
     */
    private C current;

    /**
     * Answer types as they differ on UI level. Answer type defines
     * concrete <code>AnswerWrapper</code> subclass.
     *
     * @author Konstantin Grigoriev
     */
    public static enum Type {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE,
        USER_INPUT
    }

    /**
     * Factory method for creating answer by specific type.
     *
     * @param questionDefinition question definitin in which answer will be created
     * @param type               specific amswer type
     * @return created answer instance
     */
    public static AnswerWrapper createAnswer(QuestionType questionDefinition, Type type) {
        switch (type) {
            case SINGLE_CHOICE:
                questionDefinition.setSingleChoice(new ChoiceType());
                questionDefinition.setMultipleChoice(null);
                questionDefinition.setUserInput(null);
                break;
            case MULTIPLE_CHOICE:
                questionDefinition.setMultipleChoice(new ChoiceType());
                questionDefinition.setSingleChoice(null);
                questionDefinition.setUserInput(null);
                break;
            case USER_INPUT:
                questionDefinition.setUserInput(new UserInputType());
                questionDefinition.getUserInput().setType(InputType.STRING);
                questionDefinition.setSingleChoice(null);
                questionDefinition.setMultipleChoice(null);
                break;
            default:
                throw new IllegalArgumentException("Unrecognized answer type");
        }
        return getAnswer(questionDefinition);
    }

    /**
     * Factory method for creating answer on base of question definition.
     *
     * @param questionDefinition filled previously question definition
     * @return answer instance
     */
    public static AnswerWrapper getAnswer(QuestionType questionDefinition) {
        if (questionDefinition.getSingleChoice() != null) {
            return new SingleChoiceAnswerWrapper(questionDefinition.getSingleChoice());
        } else if (questionDefinition.getMultipleChoice() != null) {
            return new MultipleChoiceAnswerWrapper(questionDefinition.getMultipleChoice());
        } else if (questionDefinition.getUserInput() != null) {
            return new UserInputAnswerWrapper(questionDefinition.getUserInput());
        } else {
            return null;
        }
    }

    /**
     * Creates new instance of wrapper, not used directly,
     *
     * @param definition specific answer definition
     */
    protected AnswerWrapper(D definition) {
        setDefinition(definition);
    }

    /**
     * Returns answer type as it defined in overriding methods.
     *
     * @return current answer type
     */
    public abstract Type getType();

    /**
     * Converts current answer definition <code>D</code>
     * to <code>ru.spbspu.staub.model.answer.AnswerType</code>.
     * Method id used during saving answer to <code>QuestionTrace</code>.
     *
     * @return converted instance
     */
    public abstract ru.spbspu.staub.model.answer.AnswerType getAnswer();

    /**
     * Method is used during saving question (during editing/creating).
     * Method transfers right answer from current to definition.
     */
    public abstract void resolveCorrectAnswer();

    /**
     * Method is used during editing existent question.
     * Method transfers right answer from definition to current.
     */
    public abstract void determineCorrectAnswer();

    public boolean isChoice() {
        return this instanceof ChoiceAnswerWrapper;
    }

    public boolean isSingleChoice() {
        return this instanceof SingleChoiceAnswerWrapper;
    }

    public boolean isMultipleChoice() {
        return this instanceof MultipleChoiceAnswerWrapper;
    }

    public boolean isUserInput() {
        return this instanceof UserInputAnswerWrapper;
    }

    public D getDefinition() {
        return definition;
    }

    public void setDefinition(D definition) {
        this.definition = definition;
    }

    public C getCurrent() {
        return current;
    }

    public void setCurrent(C current) {
        this.current = current;
    }
}
