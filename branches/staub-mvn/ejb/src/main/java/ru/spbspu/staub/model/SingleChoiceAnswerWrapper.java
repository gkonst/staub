package ru.spbspu.staub.model;

import ru.spbspu.staub.entity.QuestionTrace;
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
    private QuestionTrace questionTrace;

    public SingleChoiceAnswerWrapper(ChoiceType answerDefinition, QuestionTrace questionTrace) {
        this.answerDefinition = answerDefinition;
        this.questionTrace = questionTrace;
    }

    public QuestionTrace unwrap() {
        // TODO implement method
        // fills answer field in trace
        // fills finished field in trace
        // checks answer for correctness
        return questionTrace;
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
