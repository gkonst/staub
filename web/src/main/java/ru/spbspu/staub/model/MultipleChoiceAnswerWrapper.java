package ru.spbspu.staub.model;

import ru.spbspu.staub.model.question.ChoiceType;
import ru.spbspu.staub.model.question.AnswerType;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.ArrayList;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public class MultipleChoiceAnswerWrapper extends AnswerWrapper {
    private List<SelectItem> answerItems = null;
    private List<String> userAnswer = null;

    public MultipleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        answerItems = new ArrayList<SelectItem>();
        userAnswer = new ArrayList<String>();
        for (AnswerType choice : answerDefinition.getAnswer()) {
            answerItems.add(new SelectItem(choice.getId(), choice.getValue()));
        }
    }

    public List<SelectItem> getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(List<SelectItem> answerItems) {
        this.answerItems = answerItems;
    }

    public List<String> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(List<String> userAnswer) {
        this.userAnswer = userAnswer;
    }
}
