package ru.spbspu.staub.export;

import java.util.Date;

/**
 * Base class for export elements (cell, columns, etc.).
 *
 * @author Konstantin Grigoriev
 */
public class Item {

    protected Class type;

    public boolean isInteger() {
        return Integer.class.equals(type);
    }

    public boolean isLong() {
        return Long.class.equals(type);
    }

    public boolean isFloat() {
        return Float.class.equals(type);
    }

    public boolean isDouble() {
        return Double.class.equals(type);
    }

    public boolean isDate() {
        return Date.class.equals(type);
    }

    public boolean isBoolean() {
        return Boolean.class.equals(type);
    }

    public boolean isString() {
        return String.class.equals(type);
    }

    public boolean isNumber() {
        return isInteger() || isFloat() || isDouble() || isLong();
    }
}
