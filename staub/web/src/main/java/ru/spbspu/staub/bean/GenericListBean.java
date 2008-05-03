package ru.spbspu.staub.bean;

import org.jboss.seam.faces.DataModels;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.faces.model.DataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericListBean<T extends Serializable> extends GenericModeBean {
    private static final long serialVersionUID = -5579488872426850764L;

    private Map<Object, Boolean> selectedMap;

    private DataModel dataModel;

    private static enum PageDirection {
        NEXT_PAGE,
        PREV_PAGE,
        LAST_PAGE,
        FIRST_PAGE,
        CURRENT_PAGE,
        EXACT_PAGE
    }

    /**
     * Default value for the <code>rowsOnPage</code> instance variable.
     */
    private static final int DEFAULT_ROWS_ON_PAGE = 20;

    /**
     * Total number of pages in selection.
     */
    private int pageQuantity = 1;

    /**
     * How many rows display on every page.
     */
    private int rowsOnPage = DEFAULT_ROWS_ON_PAGE;

    /**
     * Actual number of rows in the current page.
     * Sometimes it not agrees with <code>rowsOnPage</code>.
     */
    private int rowsOnCurrentPage = DEFAULT_ROWS_ON_PAGE;

    /**
     * Total number of rows in selection.
     */
    private int rowsTotal;

    /**
     * Current page number.
     */
    private int currentPage = 1;

    /**
     * Object representation of the selection data.
     */
    protected FormTable formTable;

    /**
     * Main method for the search (abstract).
     * <p/>
     * Every specific subclass must implement this method own way.
     *
     * @param formProperties form properties
     * @return fetched form table
     */
    protected abstract FormTable findObjects(FormProperties formProperties);

    /**
     * Do some prepare operations, for example initializes some additional parameters.
     * Optinal operation.
     */
    protected void prepareBean() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initBean() {
        if (isBeanModeDefined()) {
            logger.debug("Preparing list bean...");
            prepareBean();
            switch (getBeanMode()) {
                case VIEW_MODE:     // using fall through switch behaviour
                case EDIT_MODE:
                case CREATE_MODE:
                    findFirstPageData();
                    break;
                case REFRESH_MODE:
                    doRefresh();
                    break;
                default:
                    logger.debug("  Unknown bean mode -> skipping");
            }
            logger.debug("Preparing list bean... OK");
        }
    }

    /**
     * Initializes the page in specific direction.
     * Defines next page number and retrieves corresponding data.
     *
     * @param pageDirection direction for navigation
     */
    protected void initPage(PageDirection pageDirection) {
        logger.debug("Defining page navigation direction...");
        logger.debug("  Direction is : " + pageDirection);
        switch (pageDirection) {
            case NEXT_PAGE:
                currentPage++;
                break;
            case PREV_PAGE:
                Math.max(1, --currentPage);
                break;
            case FIRST_PAGE:
                currentPage = 1;
                break;
            case LAST_PAGE:
                currentPage = pageQuantity;
                break;
            case EXACT_PAGE:
                logger.debug("    User defined destination page: " + currentPage);
                break;
            case CURRENT_PAGE:
                logger.debug("    Leaving current page number.");
                break;
            default:
                logger.debug("   No action for this direction! Use default " + PageDirection.FIRST_PAGE);
                currentPage = 1;
        }
        logger.debug("  Change current page number to " + currentPage);

        findPageData();
    }

    /**
     * Fills properties for the current selection
     * (current page number, number of pages for retrieve etc).
     *
     * @return form properties object
     */
    protected FormProperties fillFormProperties() {
        FormProperties formProperties = new FormProperties();
        formProperties.setCurrentPage(getCurrentPage());
        formProperties.setRowsOnPage(getRowsOnPage());
        return formProperties;
    }

    /**
     * Retrieves data for the <code>currentPage</code> page.
     */
    private void findPageData() {
        logger.debug("Defining start row for search...");
        int startAtRow = 0;
        if (currentPage >= 1) {
            startAtRow = (currentPage - 1) * rowsOnPage;
        }
        logger.debug("  Start row number is : " + startAtRow);

        formTable = findObjects(fillFormProperties());

        preparePageList();
    }

    /**
     * Retrieves data for the first page (initialization).
     */
    protected void findFirstPageData() {
        logger.debug("Retrieve first page...");
        logger.debug("  Define default rows on page...");
        rowsOnPage = DEFAULT_ROWS_ON_PAGE;
        rowsOnCurrentPage = DEFAULT_ROWS_ON_PAGE;
        logger.debug("    Default rows on page : " + rowsOnPage);
        selectedMap = new HashMap<Object, Boolean>();
        initPage(PageDirection.FIRST_PAGE);
        logger.debug("Retrieve completed successfully!");
    }

    /**
     * Changes rows on page for the current list.
     */
    public void changeRowsOnPage() {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    /**
     * Prepares retrieved data for display.
     * Defines additional informational characteristics.
     */
    private void preparePageList() {
        logger.debug("Preparing current page for display...");
        logger.debug("  Defining total number of rows...");
        rowsTotal = (int) formTable.getFullCount();
        logger.debug("    Total number of rows is: " + rowsTotal);

        logger.debug("  Defining number of pages..");
        int quotient = rowsTotal / rowsOnPage;
        int remainder = rowsTotal % rowsOnPage;
        if (remainder > 0) {
            pageQuantity = quotient + 1;
        } else {
            pageQuantity = quotient;
        }
        logger.debug("    Total page quantity is: " + pageQuantity);
        logger.debug("    Current page is: " + currentPage);

        if (getCurrentPage() > getPageQuantity() && getPageQuantity() > 0) {
            logger.debug("      Autoswitch to PREVIOUS page...");
            initPage(PageDirection.PREV_PAGE);
            logger.debug("      Autoswitch to PREVIOUS page... OK");
        }

        logger.debug("  Defining list of objects for display...");
        if (formTable.getRows() != null) {
            logger.debug("    Getting objects from form table");
            dataModel = DataModels.instance().getDataModel(formTable.getRows());
        } else {
            logger.debug("    Creating new objects list");
            dataModel = DataModels.instance().getDataModel(new ArrayList<T>());
        }

        logger.debug("  Defining number of rows on current page...");
        if (dataModel.getRowCount() < rowsOnPage) {
            rowsOnCurrentPage = dataModel.getRowCount();
        } else {
            rowsOnCurrentPage = rowsOnPage;
        }
        logger.debug("    Number of rows on current page is: " + rowsOnCurrentPage);

    }

    /**
     * Show/retrieve next page of selection.
     */
    public void doNextPage() {
        logger.debug("Retrieving NEXT page...");
        initPage(PageDirection.NEXT_PAGE);
        logger.debug("Retrieving NEXT page... OK");
    }

    /**
     * Show/retrieve previous page of selection.
     */
    public void doPrevPage() {
        logger.debug("Retrieving PREVIOUS page...");
        initPage(PageDirection.PREV_PAGE);
        logger.debug("Retrieving PREVIOUS page... OK");
    }

    /**
     * Show/retrieve first page of selection.
     */
    public void doFirstPage() {
        logger.debug("Retrieving FIRST page...");
        initPage(PageDirection.FIRST_PAGE);
        logger.debug("Retrieving FIRST page... OK");
    }

    /**
     * Show/retrieve last page of selection.
     */
    public void doLastPage() {
        logger.debug("Retrieving LAST page...");
        initPage(PageDirection.LAST_PAGE);
        logger.debug("Retrieving LAST page... OK");
    }

    /**
     * Refresh user defined page of selection.
     */
    public void doExactPage() {
        if (getCurrentPage() <= getPageQuantity() && getCurrentPage() >= 1) {
            logger.debug("Retrieving EXACT page...");
            initPage(PageDirection.EXACT_PAGE);
            logger.debug("Retrieving EXACT page... OK");
        } else {
            logger.debug("Page number wrong -> staying on the current page");
        }
    }

    /**
     * Refreshes bean content.
     */
    public void doRefresh() {
        logger.debug("Refreshing CURRENT page...");
        initPage(PageDirection.CURRENT_PAGE);
        logger.debug("Refreshing CURRENT page... OK");
    }

    /**
     * Defines delete operation for current bean (optional).
     */
    public void doDelete() {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    /**
     * Defines create operation for current bean (optional).
     *
     * @return navigation outcome, by default 'detail'
     */
    public String doCreate() {
        return doCreate("detail");
    }

    @SuppressWarnings("unchecked")
    protected <S> List<S> getSelectedItems() {
        List<S> result = new ArrayList<S>();
        for (Map.Entry<Object, Boolean> entry : getSelectedMap().entrySet()) {
            if (entry.getValue()) {
                result.add((S) entry.getKey());
            }
        }
        return result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageQuantity() {
        return pageQuantity;
    }

    public void setPageQuantity(int pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

    public int getRowsOnPage() {
        return rowsOnPage;
    }

    public void setRowsOnPage(int rowsOnPage) {
        this.rowsOnPage = rowsOnPage;
    }

    public int getRowsOnCurrentPage() {
        return rowsOnCurrentPage;
    }

    public void setRowsOnCurrentPage(int rowsOnCurrentPage) {
        this.rowsOnCurrentPage = rowsOnCurrentPage;
    }

    public int getRowsTotal() {
        return rowsTotal;
    }

    public void setRowsTotal(int rowsTotal) {
        this.rowsTotal = rowsTotal;
    }

    @SuppressWarnings("unchecked")
    public T getSelected() {
        return (T) dataModel.getRowData();
    }

    public Map<Object, Boolean> getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(Map<Object, Boolean> selectedMap) {
        this.selectedMap = selectedMap;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }
}
