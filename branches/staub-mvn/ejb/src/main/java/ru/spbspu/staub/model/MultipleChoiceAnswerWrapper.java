package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.AnswerType;
import ru.spbspu.staub.model.question.ChoiceType;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public class MultipleChoiceAnswerWrapper extends AnswerWrapper {
    private List<AnswerType> userAnswer;
    private ChoiceType answerDefinition;

    public MultipleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        userAnswer = new ArrayList<AnswerType>();
        this.answerDefinition = answerDefinition;
    }

    public ChoiceType getAnswerDefinition() {
        return answerDefinition;
    }

    public void setAnswerDefinition(ChoiceType answerDefinition) {
        this.answerDefinition = answerDefinition;
    }

    public List<AnswerType> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(List<AnswerType> userAnswer) {
        this.userAnswer = userAnswer;
    }
}
