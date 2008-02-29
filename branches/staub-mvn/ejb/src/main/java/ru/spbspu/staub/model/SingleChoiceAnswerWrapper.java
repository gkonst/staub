package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.ChoiceType;

import java.util.List;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public class SingleChoiceAnswerWrapper extends AnswerWrapper {
    private List answerItems = null;
    private String userAnswer;

    public SingleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        answerItems = answerDefinition.getAnswer();
    }

    public List getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(List answerItems) {
        this.answerItems = answerItems;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
