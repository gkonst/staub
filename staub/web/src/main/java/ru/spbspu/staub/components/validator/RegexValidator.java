package ru.spbspu.staub.components.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Simle JSF Validator on base of regular expressions.
 *
 * @author Konstantin Grigoriev
 */
public class RegexValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "ru.spbspu.staub.RegexValidator";

    private String regex;

    private boolean transientValue = false;

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String strValue = (String) value;
        if (!strValue.matches(regex)) {
            throw new ValidatorException(new FacesMessage("Incorrect value, does not match regular expression: " + regex));
        }
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[1];
        values[0] = regex;
        return (values);
    }


    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        regex = (String) values[0];
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean newTransientValue) {
        transientValue = newTransientValue;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
