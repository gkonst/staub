package ru.spbspu.staub.model.list;

import java.io.Serializable;

/**
 * Parameters model for preparing <code>FormTable</code>.
 *
 * @author Konstantin Grigoriev
 */
public class FormProperties implements Serializable {

    private static final long serialVersionUID = 2239732482606373365L;

    /**
     * Number of page to fetch.
     */
    private int currentPage;
    /**
     * How many rows to fetch.
     */
    private int rowsOnPage;
    /**
     * Sort option.
     */
    private SortItem sort;

    /**
     * Default constructor.
     */
    public FormProperties() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getRowsOnPage() {
        return rowsOnPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setRowsOnPage(int rowsOnPage) {
        this.rowsOnPage = rowsOnPage;
    }

    public SortItem getSort() {
        return sort;
    }

    public void setSort(SortItem sort) {
        this.sort = sort;
    }
}
