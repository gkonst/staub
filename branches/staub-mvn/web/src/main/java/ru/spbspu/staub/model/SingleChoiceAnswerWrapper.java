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
public class SingleChoiceAnswerWrapper extends AnswerWrapper {
    private ChoiceType answerDefinition;
    private List<SelectItem> answerItems = null;
    private String userAnswer;

    public SingleChoiceAnswerWrapper(ChoiceType answerDefinition) {
        this.answerDefinition = answerDefinition;
        answerItems = new ArrayList<SelectItem>();
        for (AnswerType choice : answerDefinition.getAnswer()) {
            answerItems.add(new SelectItem(choice.getValue(), choice.getValue()));
        }
    }

    public List<SelectItem> getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(List<SelectItem> answerItems) {
        this.answerItems = answerItems;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
