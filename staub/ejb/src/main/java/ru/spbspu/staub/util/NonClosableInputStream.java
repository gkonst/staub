package ru.spbspu.staub.util;

import java.io.FilterInputStream;
import java.io.InputStream;

/**
 * The <code>NonClosableInputStream</code> class is the <code>InputStream</code> with a disabled {@link #close()}
 * method.
 *
 * @author Alexander V. Elagin
 */
public class NonClosableInputStream extends FilterInputStream {
    public NonClosableInputStream(InputStream in) {
        super(in);
    }

    /**
     * Just returns.
     */
    @Override
    public void close() {
        // do nothing
    }
}
