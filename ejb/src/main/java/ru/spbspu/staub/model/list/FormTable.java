package ru.spbspu.staub.model.list;

import java.io.Serializable;
import java.util.List;

/**
 * Object representation of data selection.
 * Encapsulates paging feature, search and sort features.
 *
 * @author Konstantin Grigoriev
 */
public class FormTable implements Serializable {

    private static final long serialVersionUID = -966379577995429300L;

    /**
     * List of data items (persistent entities or Object[] instances).
     */
    private List rows = null;

    /**
     * Total elements in table.
     */
    private long fullCount;

    /**
     * Creates empty table representation.
     */
    public FormTable() {
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public List getRows() {
        return rows;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
}
