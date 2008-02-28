package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.ChoiceType;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public class MultipleChoiceAnswerWrapper extends AnswerWrapper {
    private List answerItems = null;
    private List<String> userAnswer = null;

    public MultipleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        answerItems = answerDefinition.getAnswer();
        userAnswer = new ArrayList<String>();
    }

    public List getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(List answerItems) {
        this.answerItems = answerItems;
    }

    public List<String> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(List<String> userAnswer) {
        this.userAnswer = userAnswer;
    }
}
