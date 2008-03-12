package ru.spbspu.staub.model;

import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.model.question.QuestionType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

/**
 * Wrapper encapsulates
 *
 * @author Konstantin Grigoriev
 */
public class QuestionWrapper {

    private Log logger = Logging.getLog(QuestionWrapper.class);

    private String name;
    private String description;
    private QuestionType definition;
    private Integer timeLimit = 120;

    public QuestionWrapper(Question question) {
        wrap(question);
    }

    private void wrap(Question question) {
        this.name = question.getName();
        this.timeLimit = question.getTimeLimit();
        this.definition = parseDefinitionXML(question.getDefinition());
        this.description = this.definition.getDescription();
    }

    private QuestionType parseDefinitionXML(String definitionXML) {
        logger.debug(" Parsing question definition XML...");
        try {
            logger.debug("  XML : " + definitionXML);
            JAXBContext jaxbContext = JAXBContext.newInstance("ru.spbspu.staub.model.question");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement jaxbElement = (JAXBElement) unmarshaller.unmarshal(new StreamSource(new StringReader(definitionXML)));
            QuestionType result = (QuestionType) jaxbElement.getValue();
            logger.debug("  description : " + result.getDescription());
            logger.debug("  multipleChoice : " + result.getMultipleChoice());
            logger.debug("  singleChoice : " + result.getSingleChoice());
            return result;
        } catch (JAXBException e) {
            logger.error("Error during parsing question defination XML(" + definitionXML + ")", e);
            throw new RuntimeException(e);
        }
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
