package ru.spbspu.staub.export;

/**
 * Base Column representation.
 *
 * @author Konstantin Grigoriev
 */
public class Column extends Item {

    private String label;
    private String title;

    public Column(String label, String title, Class type) {
        this.label = label;
        this.title = title;
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Column");
        sb.append("{label=").append(label);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
