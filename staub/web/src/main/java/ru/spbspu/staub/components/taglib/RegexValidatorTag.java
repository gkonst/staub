package ru.spbspu.staub.components.taglib;

import com.sun.faces.el.ELUtils;
import ru.spbspu.staub.components.validator.RegexValidator;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;
import javax.servlet.jsp.JspException;

/**
 * Tag definition for <code>RegexValidator</code> validator.
 *
 * @author Konstantin Grigoriev
 */
public class RegexValidatorTag extends ValidatorELTag {
    private static final long serialVersionUID = -1514163184375933909L;

    private ValueExpression regexExpression;

    protected Validator createValidator() throws JspException {

        RegexValidator validator = (RegexValidator) FacesContext.getCurrentInstance().getApplication().createValidator(RegexValidator.VALIDATOR_ID);

        ELContext context = FacesContext.getCurrentInstance().getELContext();

        if (regexExpression != null) {
            String regex;
            if (!regexExpression.isLiteralText()) {
                regex = ((String) ELUtils.evaluateValueExpression(regexExpression, context));
            } else {
                regex =
                        String.valueOf(regexExpression.getExpressionString());
            }
            validator.setRegex(regex);
        }
        return null;
    }

    public void setRegexp(ValueExpression regexExpression) {
        this.regexExpression = regexExpression;
    }
}
