package ru.spbspu.staub.model;

import ru.spbspu.staub.entity.QuestionTrace;
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
    private QuestionTrace questionTrace;

    public MultipleChoiceAnswerWrapper(ChoiceType answerDefinition, QuestionTrace questionTrace) {
        userAnswer = new ArrayList<AnswerType>();
        this.answerDefinition = answerDefinition;
        this.questionTrace = questionTrace;
    }

    public QuestionTrace unwrap() {
        // TODO implement method
        // fills answer field in trace
        // fills finished field in trace
        return questionTrace;
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
