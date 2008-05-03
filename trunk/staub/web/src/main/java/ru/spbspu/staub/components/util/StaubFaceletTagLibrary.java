package ru.spbspu.staub.components.util;

import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.AbstractTagLibrary;
import ru.spbspu.staub.components.function.Functions;
import ru.spbspu.staub.components.validator.RegexValidator;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Facelet library definition for tags in Staub Application.
 *
 * @author Konstantin Grigoriev
 */
public final class StaubFaceletTagLibrary extends AbstractTagLibrary {

    public final static String NAMESPACE = "http://spbspu.ru/staub/taglib";

    public StaubFaceletTagLibrary() {
        super(NAMESPACE);
        this.addValidator("validateRegex", RegexValidator.VALIDATOR_ID);
        this.addFunction("asList", getFunction("asList", Collection.class));
    }

    private Method getFunction(String name, Class... parameterTypes) {
        try {
            return Functions.class.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new FaceletException(e);
        }
    }
}
