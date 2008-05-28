package ru.spbspu.staub.components.util;

import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.jsf.html.AbstractHtmlLibrary;
import ru.spbspu.staub.components.function.Functions;
import ru.spbspu.staub.components.html.HtmlSelectBooleanRadio;
import ru.spbspu.staub.components.renderkit.BooleanRadioRenderer;
import ru.spbspu.staub.components.validator.RegexValidator;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Facelet library definition for tags in Staub Application.
 *
 * @author Konstantin Grigoriev
 */
public final class StaubFaceletTagLibrary extends AbstractHtmlLibrary {

    public final static String NAMESPACE = "http://spbspu.ru/staub/taglib";

    public StaubFaceletTagLibrary() {
        super(NAMESPACE);
        this.addValidator("validateRegex", RegexValidator.VALIDATOR_ID);
        this.addFunction("asList", getFunction("asList", Collection.class));
        this.addHtmlComponent("selectBooleanRadio", HtmlSelectBooleanRadio.COMPONENT_TYPE, BooleanRadioRenderer.RENDERER_TYPE);
    }

    private Method getFunction(String name, Class... parameterTypes) {
        try {
            return Functions.class.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new FaceletException(e);
        }
    }
}
