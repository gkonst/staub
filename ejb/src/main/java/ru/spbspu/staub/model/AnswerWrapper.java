package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.QuestionType;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class AnswerWrapper {
    public static AnswerWrapper getAnswer(QuestionType definition) {
        if (definition.getSingleChoice() != null) {
            return new SingleChoiceAnswerWrapper(definition.getSingleChoice());
        } else if (definition.getMultipleChoice() != null) {
            return new MultipleChoiceAnswerWrapper(definition.getMultipleChoice());
        } else {
            return null;
        }
    }

    public abstract ru.spbspu.staub.model.answer.AnswerType getAnswer();

    public boolean isSingleChoice() {
        return this instanceof SingleChoiceAnswerWrapper;
    }

    public boolean isMultipleChoice() {
        return this instanceof MultipleChoiceAnswerWrapper;
    }
}
