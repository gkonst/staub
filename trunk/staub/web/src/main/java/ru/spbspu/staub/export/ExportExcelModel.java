package ru.spbspu.staub.export;

import jxl.CellView;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.beanutils.PropertyUtils;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Export to excel model represantation.
 * TODO Method call order is important.
 *
 * @author Konstantin Grigoriev
 */
public class ExportExcelModel {

    private static final Log logger = Logging.getLog(ExportExcelModel.class);

    /**
     * Maximum count of rows on sheet.
     */
    private static final int MAX_SHEET_ROWS_NUMBER = 65000;
    /**
     * Maximum default column header width.
     */
    private static final int MAX_DEFAULT_HEADER_COLUMN_WIDTH = 100;
    /**
     * Default font size.
     */
    private static final int DEFAULT_FONT_SIZE = 10;
    /**
     * Indent, used in calculating columns width.
     */
    private static final int COLUMN_WIDTH_INDENT = 10;
    /**
     * Date format with time for use in workbook.
     */
    private DateFormat dateTimeFormat = null;
    /**
     * Date with time cell format.
     */
    private WritableCellFormat cellDateTimeFormat = null;

    private WritableWorkbook workbook;
    private WritableSheet sheet;
    private int currentSheet = 0;
    private int currentRowCount = 0;

    /**
     * Map which store columns width.
     */
    private Map<Integer, Integer> columnsWidths;

    private ByteArrayOutputStream res;

    public ExportExcelModel(String title) throws ExportExcelException {
        this();
        setTitle(title);
    }

    public ExportExcelModel() throws ExportExcelException {
        logger.debug(" Creating a new workbook...");
        try {
            res = new ByteArrayOutputStream();
            workbook = Workbook.createWorkbook(res);
        } catch (IOException e) {
            throw new ExportExcelException("error during creating workbook", e);
        }
        sheet = createNewSheet(workbook);
        //TODO move datetime pattern to consts
        dateTimeFormat = new DateFormat("dd.MM.yyyy H:mm:ss");
        cellDateTimeFormat = new WritableCellFormat(dateTimeFormat);
        columnsWidths = new HashMap<Integer, Integer>();
        logger.debug(" Creating a new workbook...Ok");
    }

    public void setTitle(String title) throws ExportExcelException {
        final WritableFont mainHeaderFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
        try {
            logger.debug("   setting title : #0", title);
            sheet.addCell(new Label(0, 0, title, new WritableCellFormat(mainHeaderFont)));
        } catch (WriteException e) {
            throw new ExportExcelException("error during setting title", e);
        }
        currentRowCount = 2;
    }

    public void setHeader(List<Cell[]> headerRows) throws ExportExcelException {
        for (Cell[] headerRow : headerRows) {
            logger.debug("   adding header row : #0", Arrays.toString(headerRow));
            for (int i = 0; i < headerRow.length; i++) {
                try {
                    sheet.addCell(getWritableCell(headerRow[i], headerRow[i].getValue(), i));
                } catch (WriteException e) {
                    throw new ExportExcelException("error during adding header cell", e);
                }
            }
            currentRowCount++;
        }
        currentRowCount += 2;
    }

    public void setColumnHeaders(List<Column> columns) throws ExportExcelException {
        logger.debug("   organizing columns headers...");
        String title;
        CellView cellView;
        int columnIndex = 0;
        for (Column column : columns) {
            logger.debug("    column : #0", column);
            title = column.getTitle();
            if (title == null) {
                title = column.getLabel();
            }
            cellView = new CellView();
            try {
                cellView.setFormat(getCellFormat(column, false));
            } catch (WriteException e) {
                throw new ExportExcelException("error during formatting columns", e);
            }
            sheet.setColumnView(columnIndex, cellView);
            try {
                sheet.addCell(new Label(columnIndex, currentRowCount, title, getCellFormat(column, true)));
            } catch (WriteException e) {
                throw new ExportExcelException("error during adding column header cell", e);
            }
            columnsWidths.put(columnIndex, column.getTitle().length() + 4); // 4 - just an indent
            columnIndex++;
        }
        currentRowCount++;
        logger.debug("   organizing columns headers...Ok");
    }

    public void addData(List<Column> columns, List rows) throws ExportExcelException {
        for (Object row : rows) {
            if (currentRowCount == MAX_SHEET_ROWS_NUMBER) {
                logger.debug("    Current row count on sheet has reached MAX_SHEET_ROWS_NUMBER. Creating a new sheet");
                currentRowCount = 0;
                sheet = createNewSheet(workbook);
                setColumnHeaders(columns);
            }
            int columnIndex = 0;
            for (Column column : columns) {
                Object value;
                if (row instanceof Object[]) {
                    value = ((Object[]) row)[columnIndex]; // for select based fetching
                } else {
                    //value = HibernateUtil.getPropertyValue(row.getClass(), row, column.getLabel()); // for persistent based fetching
                    try {
                        value = PropertyUtils.getProperty(row, column.getLabel());
                    } catch (IllegalAccessException e) {
                        throw new ExportExcelException("error during getting value for property : " + column.getLabel(), e);
                    } catch (InvocationTargetException e) {
                        throw new ExportExcelException("error during getting value for property : " + column.getLabel(), e);
                    } catch (NoSuchMethodException e) {
                        throw new ExportExcelException("error during getting value for property : " + column.getLabel(), e);
                    }
                }
                if (value == null) {
                    columnIndex++;
                    continue;
                } else { // if value not null we getting it length and comapring with current
                    // column width. If value's length more than current width we write
                    // it to the columnsWidths HashMap instead of current column width

                    if ((value.toString()).length() > columnsWidths.get(columnIndex)) {
                        logger.debug("    correcting width for column(i=#0, label=#1) oldWidth=#2, newWidth=#3", columnIndex, column.getLabel(), columnsWidths.get(columnIndex), value.toString().length());
                        columnsWidths.put(columnIndex, value.toString().length());
                    }
                }
                try {
                    sheet.addCell(getWritableCell(column, value, columnIndex));
                } catch (WriteException e) {
                    throw new ExportExcelException("error during adding data cell", e);
                }
                columnIndex++;
            }
            currentRowCount++;
        }
        fixWidth();
    }

    public byte[] getData() throws ExportExcelException {
        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            throw new ExportExcelException("error during writing&closing workbook", e);
        } catch (WriteException e) {
            throw new ExportExcelException("error during writing&closing workbook", e);
        }
        return res.toByteArray();
    }

    /**
     * Creates a new sheet in Excel workbook.
     *
     * @param workbook Excel workbook
     * @return created <code>WritableSheet</code>
     */
    private WritableSheet createNewSheet(WritableWorkbook workbook) {
        WritableSheet sheet = workbook.createSheet("export" + " " + (currentSheet + 1), currentSheet);
        currentSheet++;
        return sheet;
    }

    /**
     * Creates <code>WritableCell</code> with data.
     *
     * @param cell        column in which data are writing
     * @param value       value to write
     * @param columnIndex index in column of cell in which data are writing
     * @return cell with data
     */
    private WritableCell getWritableCell(Item cell, Object value, int columnIndex) {
        WritableCell writableCell;
        int integerFlagValue;
        //TODO: take into account 4 types: double, float, integer and long
        if (cell.isNumber()) {
            writableCell =
                    new Number(columnIndex, currentRowCount, new Double(value.toString().replace(",", ".")));
        } else if (cell.isBoolean()) {
            integerFlagValue =
                    (Boolean.valueOf(value.toString()).equals(true)) ? 1 : 0;
            writableCell =
                    new Number(columnIndex, currentRowCount, (double) integerFlagValue);
        } else if (cell.isDate()) {
            writableCell = new DateTime(columnIndex, currentRowCount, (Date) value, cellDateTimeFormat);
        } else {
            writableCell = new Label(columnIndex, currentRowCount, value.toString().trim());
        }
        return writableCell;
    }

    /**
     * Get cell format for column.
     *
     * @param column       column
     * @param headerFormat indicates is it header cell or not
     * @return cell format for column
     * @throws WriteException if smth wrong
     */
    private WritableCellFormat getCellFormat(Column column,
                                             boolean headerFormat) throws WriteException {
        WritableFont dataFont =
                new WritableFont(WritableFont.ARIAL, DEFAULT_FONT_SIZE, WritableFont.NO_BOLD,
                        false); //It will better to remove it at class level, but application crashed when did it TODO find decision of this problem
        WritableFont headerFont = new WritableFont(WritableFont.ARIAL, DEFAULT_FONT_SIZE, WritableFont.BOLD, false);
        WritableCellFormat cellFormat;
        WritableFont font = headerFormat ? headerFont : dataFont;

        DisplayFormat displayFormat;

        if (column.isInteger() || column.isLong()) {
            displayFormat = NumberFormats.INTEGER;
        } else if (column.isDouble() || column.isFloat()) {
            displayFormat = NumberFormats.FLOAT;
        } else if (column.isString()) {
            displayFormat = NumberFormats.TEXT;
        } else if (column.isBoolean()) {
            displayFormat = NumberFormats.INTEGER;
        } else if (column.isDate()) {
            displayFormat = DateFormats.DEFAULT;
        } else {
            displayFormat = NumberFormats.TEXT;
        }

        cellFormat = new WritableCellFormat(font, displayFormat);
        cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
        cellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THICK); //setting thick border for header cells
        return cellFormat;
    }

    private void fixWidth() {
        CellView cellView = new CellView();
        int columnWidth;
        for (int i = 0; i < sheet.getColumns(); i++) {
            columnWidth = columnsWidths.get(i) + COLUMN_WIDTH_INDENT < MAX_DEFAULT_HEADER_COLUMN_WIDTH ? columnsWidths.get(i) + COLUMN_WIDTH_INDENT : MAX_DEFAULT_HEADER_COLUMN_WIDTH; // 10 - just an indent
            logger.debug("  setting column #0 width = #1", i, columnWidth);
            cellView.setSize(columnWidth * 256);
            sheet.setColumnView(i, cellView);
        }
    }
}
