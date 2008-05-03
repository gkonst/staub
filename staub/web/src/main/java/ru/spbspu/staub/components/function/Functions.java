package ru.spbspu.staub.components.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JSTL Functions class.
 *
 * @author Konstantin Grigoriev
 */
public class Functions {

    public static <T> List<T> asList(Collection<T> collection) {
        return new ArrayList<T>(collection);
    }
}
