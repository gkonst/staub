package ru.spbspu.staub.components.util;

import com.sun.facelets.tag.AbstractTagLibrary;
import ru.spbspu.staub.components.validator.RegexValidator;

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
    }
}
