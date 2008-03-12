package ru.spbspu.staub.model;

import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.model.question.QuestionType;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class AnswerWrapper {
    public static AnswerWrapper getAnswer(QuestionType definition, QuestionTrace questionTrace) {
        if (definition.getSingleChoice() != null) {
            return new SingleChoiceAnswerWrapper(definition.getSingleChoice(), questionTrace);
        } else if (definition.getMultipleChoice() != null) {
            return new MultipleChoiceAnswerWrapper(definition.getMultipleChoice(), questionTrace);
        } else {
            return null;
        }
    }

    public abstract QuestionTrace unwrap();

    public boolean isSingleChoice() {
        return this instanceof SingleChoiceAnswerWrapper;
    }

    public boolean isMultipleChoice() {
        return this instanceof MultipleChoiceAnswerWrapper;
    }
}
