package ru.spbspu.staub.components.converter;

import ru.spbspu.staub.components.util.MessageFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.util.StringTokenizer;

/**
 * Custom JSF Converter for converting seconds as <code>Integer</code>
 * to time as <code>String</code>.
 *
 * @author Konstantin Grigoriev
 */
public class TimeConverter implements Converter {

    private static final String TIME_PATTERN = "(?:(?:[01]\\d)|(?:2[0-3]):)?[0-5]\\d:[0-5]\\d";

    /**
     * {@inheritDoc}
     */
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.matches(TIME_PATTERN)) {
            throw new ConverterException(MessageFactory.getMessage(
                    context, "ru.spbspu.staub.TimeConverter", value,
                    "01:10:00",
                    MessageFactory.getLabel(context, component)));
        }
        Integer result = 0;
        StringTokenizer st = new StringTokenizer(value, ":");
        String token;
        if (st.countTokens() == 3) {
            token = st.nextToken();
            result = 3600 * Integer.valueOf(token);
        }
        token = st.nextToken();
        result = result + 60 * Integer.valueOf(token);
        token = st.nextToken();
        result = result + Integer.valueOf(token);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        int seconds = (Integer) value;
        int minutes = 0;
        int hours = 0;
        if (seconds >= 60) {
            minutes = seconds / 60;
            seconds = seconds % 60;
            if (minutes >= 60) {
                hours = minutes / 60;
                minutes = minutes % 60;
            }
        }
        StringBuilder result = new StringBuilder();
        if (hours != 0) {
            result.append(lpad(String.valueOf(hours), 2, "0"));
            result.append(":");
        }
        result.append(lpad(String.valueOf(minutes), 2, "0"));
        result.append(":");
        result.append(lpad(String.valueOf(seconds), 2, "0"));
        return result.toString();
    }

    private String lpad(String string, int n, String symbol) {
        StringBuilder result = new StringBuilder(string);
        while (result.length() < n) {
            result.insert(0, symbol);
        }
        return result.toString();
    }
}
