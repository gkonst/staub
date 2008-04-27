package ru.spbspu.staub.exception;

/**
 * The <code>RemoveException</code> thrown if a remove operation could not be performed.
 *
 * @author Alexander V. Elagin
 */
public class RemoveException extends Exception {
    private static final long serialVersionUID = 1297160822207887736L;

    public RemoveException(String message) {
        super(message);
    }

    public RemoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoveException(Throwable cause) {
        super(cause);
    }
}
