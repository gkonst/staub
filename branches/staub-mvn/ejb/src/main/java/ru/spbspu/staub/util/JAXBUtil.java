package ru.spbspu.staub.util;

import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.question.QuestionType;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * JAXB utility class.
 * Contains methods for marshalling and marshalling objects.
 *
 * @author Konstantin Grigoriev
 */
public final class JAXBUtil {
    private static final String QUESTION_SCHEMA_PACKAGE = "ru.spbspu.staub.model.question";

    private static final QName QUESTION_SCHEMA_QNAME = new QName("http://staub.spbspu.ru/Question", "question");

    private static final String ANSWER_SCHEMA_PACKAGE = "ru.spbspu.staub.model.answer";

    private static final QName ANSWER_SCHEMA_QNAME = new QName("http://staub.spbspu.ru/Answer", "answer");

    private static final Log logger = Logging.getLog(JAXBUtil.class);

    private JAXBUtil() {
        // do nothing
    }

    /**
     * Parses(unmarshalls) definition xml string to <code>QuestionType</code> object using JAXB.
     *
     * @param definitionXML string to parse
     *
     * @return unmarshalled object
     */
    public static QuestionType parseQuestionXML(String definitionXML) {
        try {
            logger.debug(" Parsing question definition XML...");
            logger.debug("  XML : #0", definitionXML);
            JAXBContext jaxbContext = JAXBContext.newInstance(QUESTION_SCHEMA_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement jaxbElement = (JAXBElement) unmarshaller.unmarshal(new StreamSource(new StringReader(definitionXML)));
            QuestionType result = (QuestionType) jaxbElement.getValue();
            logger.debug("  description : #0", result.getDescription());
            logger.debug("  multipleChoice : #0", result.getMultipleChoice());
            logger.debug("  singleChoice : #0", result.getSingleChoice());
            logger.debug(" Parsing question definition XML...Ok");
            return result;
        } catch (JAXBException e) {
            logger.error("Error during parsing question definition XML(" + definitionXML + ")", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates(marshalls) xml string from <code>QuestionType</code> object using JAXB.
     *
     * @param questionType object to marshall
     *
     * @return created xml string
     */
    public static String createQuestionXML(QuestionType questionType) {
        try {
            logger.debug(" Creating question definition XML...");
            JAXBContext jaxbContext = JAXBContext.newInstance(QUESTION_SCHEMA_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter out = new StringWriter();
            JAXBElement<QuestionType> jaxbElement = new JAXBElement<QuestionType>(QUESTION_SCHEMA_QNAME, QuestionType.class, null, questionType);
            marshaller.marshal(jaxbElement, out);
            String result = out.toString();
            logger.debug("  result xml : #0", result);
            logger.debug(" Creating question definition XML...Ok");
            return result;
        } catch (JAXBException e) {
            logger.error("Error during creating question definition XML", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses(unmarshalls) xml string to <code>AnswerType</code> object using JAXB.
     *
     * @param answerXml string to parse
     *
     * @return unmarshalled object
     */
    public static AnswerType parseAnswerXML(String answerXml) {
        try {
            logger.debug(" Parsing answer XML...");
            logger.debug("  XML : #0", answerXml);
            JAXBContext jaxbContext = JAXBContext.newInstance(ANSWER_SCHEMA_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement jaxbElement = (JAXBElement) unmarshaller.unmarshal(new StreamSource(new StringReader(answerXml)));
            AnswerType result = (AnswerType) jaxbElement.getValue();
            logger.debug("  multipleChoice : #0", result.getMultipleChoice());
            logger.debug("  singleChoice : #0", result.getSingleChoice());
            logger.debug(" Parsing answer XML...Ok");
            return result;
        } catch (JAXBException e) {
            logger.error("Error during parsing answer XML(" + answerXml + ")", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates(marshalls) xml string from <code>AnswerType</code> object using JAXB.
     *
     * @param answerType object to marshall
     *
     * @return created xml string
     */
    public static String createAnswerXML(AnswerType answerType) {
        try {
            logger.debug(" Creating answer XML...");
            JAXBContext jaxbContext = JAXBContext.newInstance(ANSWER_SCHEMA_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter out = new StringWriter();
            JAXBElement<AnswerType> jaxbElement = new JAXBElement<AnswerType>(ANSWER_SCHEMA_QNAME, AnswerType.class, null, answerType);
            marshaller.marshal(jaxbElement, out);
            String result = out.toString();
            logger.debug("  result xml : #0", result);
            logger.debug(" Creating answer XML...Ok");
            return result;
        } catch (JAXBException e) {
            logger.error("Error during creating answer XML", e);
            throw new RuntimeException(e);
        }
    }
}
