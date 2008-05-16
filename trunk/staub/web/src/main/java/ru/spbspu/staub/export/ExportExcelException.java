package ru.spbspu.staub.export;

/**
 * The <code>ExportExcelException</code> thrown if a export operation could not be performed.
 *
 * @author Konstantin Grigoriev
 */
public class ExportExcelException extends Exception {
    private static final long serialVersionUID = 4500449752107697747L;

    public ExportExcelException(String message) {
        super(message);
    }

    public ExportExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExportExcelException(Throwable cause) {
        super(cause);
    }
}
