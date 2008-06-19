package ru.spbspu.staub.bean;

import org.apache.commons.beanutils.PropertyUtils;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.export.Cell;
import ru.spbspu.staub.export.Column;
import ru.spbspu.staub.export.ExportExcelException;
import ru.spbspu.staub.export.ExportExcelModel;
import ru.spbspu.staub.util.DownloadResource;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Generic webbean implementation which used for exporting list data.
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericExportableListBean<T extends Serializable> extends GenericListBean<T> {
    private static final long serialVersionUID = -7751139125684026460L;
    /**
     * Rows on the every page in case of export to excel operation.
     */
    private static final int EXCEL_ROWS_ON_PAGE = 100;
    /**
     * Default name of exported file.
     */
    private static final String DEFAULT_FILENAME = "export";

    /**
     * Bundle prefix for constructing key in bundles for column titles and list title
     * (for example <code>"test.trace.list."</code>).
     *
     * @return bundle prefix
     */
    public abstract String getBundlePrefix();

    /**
     * Array of column names for exporting defined in nested form
     * (for example <code>"test.name"</code>).
     * Columns names used for fetching property values and for contructing
     * keys in bundle for column titles.
     *
     * @return array of column labels
     */
    public abstract String[] getColumns();

    /**
     * Returns list of rows of cells which will be added to header.
     * May be overrided for adding some fields.
     * By default add one header row - date of creation.
     *
     * @return list of rows
     */
    protected List<Cell[]> getHeader() {
        List<Cell[]> header = new ArrayList<Cell[]>();
        header.add(new Cell[]{new Cell(getBundledString("system.export.exportDate")), new Cell(new Date())});
        return header;
    }

    /**
     * Returns name of exported file.
     * May be overrided, by default - 'export'.
     *
     * @return name of file
     */
    protected String getFilename() {
        return DEFAULT_FILENAME;
    }

    /**
     * Exports list data into excel.
     *
     * @throws ru.spbspu.staub.export.ExportExcelException
     *          if smth wrong during export
     */
    public void doExport() throws ExportExcelException {
        logger.debug("Export data...");
        int currentRowsOnPage = getRowsOnPage();
        int currentPage = getCurrentPage();
        try {
            setRowsOnPage(EXCEL_ROWS_ON_PAGE);
            ExportExcelModel exportExcelModel = new ExportExcelModel(getBundledString(getBundlePrefix() + "title"));
            exportExcelModel.setHeader(getHeader());
            List<Column> exportColumns = new ArrayList<Column>();
            for (int i = 1; i <= getPageQuantity(); i++) {
                setCurrentPage(i);
                doExactPage();
                if (i == 1 && getRowsOnCurrentPage() > 0) {
                    if(getColumns() == null || getColumns().length == 0) {
                        throw new ExportExcelException("No columns in getColumns()");
                    }
                    for (String label : getColumns()) {
                        String title = getBundledString(getBundlePrefix() + label);
                        Class type = PropertyUtils.getPropertyType(formTable.getRows().get(0), label);
                        exportColumns.add(new Column(label, title, type));
                    }
                    exportExcelModel.setColumnHeaders(exportColumns);
                }
                exportExcelModel.addData(exportColumns, formTable.getRows());
            }

            byte[] data = exportExcelModel.getData();

            Contexts.getSessionContext().set(DownloadResource.SESSION_KEY, data);
            Contexts.getSessionContext().set(DownloadResource.FILENAME_KEY, getFilename());
            logger.debug("Export data...Ok");
        } catch (InvocationTargetException e) {
            throw new ExportExcelException("error during getting column type", e);
        } catch (NoSuchMethodException e) {
            throw new ExportExcelException("error during getting column type", e);
        } catch (IllegalAccessException e) {
            throw new ExportExcelException("error during getting column type", e);
        } finally {
            setRowsOnPage(currentRowsOnPage);
            setCurrentPage(currentPage);
        }
    }

    /**
     * Cancels export, clears session attributes.
     */
    public void doCancelExport() {
        Contexts.getSessionContext().set(DownloadResource.SESSION_KEY, null);
        Contexts.getSessionContext().set(DownloadResource.FILENAME_KEY, null);
    }

}
