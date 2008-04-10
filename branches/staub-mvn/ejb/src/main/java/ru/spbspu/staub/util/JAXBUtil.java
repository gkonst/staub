package ru.spbspu.staub.util;

import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.model.question.QuestionType;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JAXB utility class.
 * Contains methods for marshalling and marshalling objects.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
public final class JAXBUtil {
    private static final Map<Class, QName> qNameMap;

    private static final Log logger = Logging.getLog(JAXBUtil.class);

    static {
        HashMap<Class, QName> map = new HashMap<Class, QName>();
        map.put(QuestionType.class, new QName("http://staub.spbspu.ru/Question", "question"));
        map.put(AnswerType.class, new QName("http://staub.spbspu.ru/Answer", "answer"));
        qNameMap = Collections.unmodifiableMap(map);
    }

    private JAXBUtil() {
        // do nothing
    }

    /**
     * Unmarshalls an instance of the POJO class.
     *
     * @param pojoClass   the <code>Class</code> to unmarshall
     * @param inputStream the xml data stream
     *
     * @return the unmarshalled POJO class
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseXml(Class<T> pojoClass, InputStream inputStream) {
        if (pojoClass == null) {
            String message = "Parameter pojoClass can not be null.";
            throw new IllegalArgumentException(message);
        }

        if (!qNameMap.containsKey(pojoClass)) {
            String message = MessageFormat.format("Unmarshalling an instance of the {0} class is not supported.",
                    pojoClass.getName());
            throw new UnsupportedOperationException(message);
        }

        try {
            logger.debug(" Unmarshalling xml...");
            logger.debug("  pojoClass : #0", pojoClass.getName());
            JAXBContext jaxbContext = JAXBContext.newInstance(pojoClass.getPackage().getName());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement jaxbElement = (JAXBElement) unmarshaller.unmarshal(inputStream);
            T result = (T) jaxbElement.getValue();
            logger.debug("  result : #0", result);
            logger.debug(" Unmarshalling xml...Ok");
            return result;
        } catch (JAXBException e) {
            logger.error("Could not unmarshall an xml.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Marshalls an instance of the POJO class.
     *
     * @param pojo the instance to marshall
     *
     * @return the marshalled xml data
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] createXml(T pojo) {
        if (pojo == null) {
            String message = "Parameter pojo can not be null.";
            throw new IllegalArgumentException(message);
        }

        Class pojoClass = pojo.getClass();

        if (!qNameMap.containsKey(pojoClass)) {
            String message = MessageFormat.format("Marshalling an instance of the {0} class is not supported.",
                    pojoClass.getName());
            throw new UnsupportedOperationException(message);
        }

        try {
            logger.debug(" Marshalling xml...");
            logger.debug("  pojoClass : #0", pojoClass.getName());
            JAXBContext jaxbContext = JAXBContext.newInstance(pojoClass.getPackage().getName());
            Marshaller marshaller = jaxbContext.createMarshaller();
            JAXBElement jaxbElement = new JAXBElement(qNameMap.get(pojoClass), pojoClass, null, pojo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(jaxbElement, baos);
            byte[] result = baos.toByteArray();
            logger.debug(" Marshalling xml...Ok");
            return result;
        } catch (JAXBException e) {
            logger.error("Could not marshall an xml.", e);
            throw new RuntimeException(e);
        }
    }
}
