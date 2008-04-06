package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.AnswerType;
import ru.spbspu.staub.model.question.ChoiceType;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public class SingleChoiceAnswerWrapper extends AnswerWrapper {
    private ChoiceType answerDefinition;
    private AnswerType userAnswer;

    public SingleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        this.answerDefinition = answerDefinition;
    }

    public AnswerType getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(AnswerType userAnswer) {
        this.userAnswer = userAnswer;
    }

    public ChoiceType getAnswerDefinition() {
        return answerDefinition;
    }

    public void setAnswerDefinition(ChoiceType answerDefinition) {
        this.answerDefinition = answerDefinition;
    }
}
