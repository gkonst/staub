package ru.spbspu.staub.model;

import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.util.JAXBUtil;

/**
 * Wrapper encapsulates basic <code>Question</code> fields
 * with paresed xml fields.
 * TODO may be useless
 *
 * @author Konstantin Grigoriev
 */
public class QuestionWrapper {

    private String name;
    private String description;
    private QuestionType definition;
    private Integer timeLimit;

    public QuestionWrapper(Question question) {
        wrap(question);
    }

    private void wrap(Question question) {
        this.name = question.getName();
        this.timeLimit = question.getTimeLimit();
        this.definition = JAXBUtil.parseQuestionXML(question.getDefinition());
        this.description = this.definition.getDescription();
    }

    public boolean isTimeLimitPresent() {
        return timeLimit != null && timeLimit != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public QuestionType getDefinition() {
        return definition;
    }

    public void setDefinition(QuestionType definition) {
        this.definition = definition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
