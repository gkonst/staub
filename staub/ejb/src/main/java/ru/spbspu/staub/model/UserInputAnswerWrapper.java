package ru.spbspu.staub.model;

import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.question.UserInputType;

/**
 * The <code>UserInputAnswerWrapper</code> class is a holder for answer related data. This class is for user input
 * questions answers.
 *
 * @author Alexander V. Elagin
 */
public class UserInputAnswerWrapper extends AnswerWrapper {
    private UserInputType userInputType;

    private String userInput;

    public UserInputAnswerWrapper(UserInputType userInputType) {
        this.userInputType = userInputType;
    }

    public UserInputType getUserInputType() {
        return userInputType;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public AnswerType getAnswer() {
        AnswerType answerType = new AnswerType();
        answerType.setUserInput(userInput);
        return answerType;
    }
}
