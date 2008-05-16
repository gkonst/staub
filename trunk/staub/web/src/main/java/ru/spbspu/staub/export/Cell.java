package ru.spbspu.staub.export;

/**
 * Base Cell represantation for export, type depends on value class.
 *
 * @author Konstantin Grigoriev
 */
public class Cell extends Item {

    private Object value;

    public Cell(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("value can't be null");
        }
        this.value = value;
        this.type = value.getClass();
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cell");
        sb.append("{value=").append(value);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
