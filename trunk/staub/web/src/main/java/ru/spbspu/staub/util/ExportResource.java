package ru.spbspu.staub.util;

import static org.jboss.seam.ScopeType.APPLICATION;
import org.jboss.seam.annotations.Install;
import static org.jboss.seam.annotations.Install.BUILT_IN;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.log.Log;
import org.jboss.seam.servlet.ContextualHttpServletRequest;
import org.jboss.seam.web.AbstractResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Seam component for exporting byte data from session to file (as attachment to response).
 *
 * @author Konstantin Grigoriev
 */
@Scope(APPLICATION)
@Name("ru.spbspu.staub.exportResource")
@Install(precedence = BUILT_IN)
@BypassInterceptors
public class ExportResource extends AbstractResource {

    @Logger
    private Log logger;

    public static final String RESOURCE_PATH = "/export";

    /**
     * Http Session key for storing data to export.
     */
    public static final String SESSION_KEY = "STAUB-EXPORT-DATA";
    /**
     * Http Session key for storing file name to export data.
     */
    public static final String FILENAME_KEY = "STAUB-EXPORT-FILENAME";
    /**
     * Http Session key for storing content type of export data.
     */
    public static final String CONTENT_TYPE_KEY = "STAUB-EXPORT-CONTENT-TYPE";

    /**
     * Defines supported content types.
     */
    private static enum ContentType {
        TEXT("text/html; charset=utf-8", ".txt"),
        EXCEL("application/vnd.ms-excel; charset=utf-8", ".xls"),
        ZIP("application/zip; charset=utf-8", ".zip");

        private String realContentType;
        private String fileExtension;

        /**
         * Creates content type with specific real type.
         *
         * @param realContentType real type
         * @param fileExtension   file extension
         */
        ContentType(String realContentType, String fileExtension) {
            this.realContentType = realContentType;
            this.fileExtension = fileExtension;
        }

        public String getRealContentType() {
            return this.realContentType;
        }

        public String getFileExtension() {
            return fileExtension;
        }
    }

    /**
     * Whitespace code in ASCII in hex.
     */
    private static final int WHITESPACE_ASCII_CODE = 0x20;

    /**
     * Default encoding for the servlet output.
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    @Override
    public String getResourcePath() {
        return RESOURCE_PATH;
    }

    @Override
    public void getResource(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        new ContextualHttpServletRequest(request) {
            @Override
            public void process() throws IOException {
                doWork(request, response);
            }
        }.run();

    }

    private void doWork(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("getting attribute from request...");
        String contentTypeName = request.getParameter(CONTENT_TYPE_KEY);
        ContentType contentType = ContentType.valueOf(contentTypeName);
        logger.debug("  content type is: " + contentType);
        response.setContentType(contentType.getRealContentType());
        logger.debug("getting attribute from session...");
        String filename = (String) Contexts.getSessionContext().get(FILENAME_KEY);
        logger.debug("  file name is: " + filename);

        byte[] exportData = (byte[]) Contexts.getSessionContext().get(SESSION_KEY);

        logger.debug("  export data is: " + String.valueOf(exportData));

        if (exportData == null) {
            logger.error("export failed, data is null");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {

            logger.debug(" writing to output stream... ");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + makeUTF8Filename(filename + contentType.getFileExtension()) + "\"");

            if (ContentType.ZIP.equals(contentType)) {
                ZipOutputStream stream = new ZipOutputStream(response.getOutputStream());
                stream.putNextEntry(new ZipEntry(makeUTF8Filename(filename)));
                stream.write(exportData);
                stream.close();
            } else {
                response.setContentLength(exportData.length);
                response.getOutputStream().write(exportData);
            }

            response.flushBuffer();
            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.debug(" export finished ");

            Contexts.getSessionContext().set(SESSION_KEY, null);
            Contexts.getSessionContext().set(FILENAME_KEY, null);
        }
    }

    /**
     * Converts source file name to UTF-8 encoding.
     *
     * @param filename file name
     * @return converted file name
     */
    private String makeUTF8Filename(String filename) {
        try {
            byte[] bytes = filename.getBytes(DEFAULT_ENCODING);
            StringBuffer result = new StringBuffer();
            for (byte aByte : bytes) {
                if (aByte >= WHITESPACE_ASCII_CODE) {
                    result.append((char) aByte);
                } else {
                    result.append("%").append(Long.toHexString(aByte < 0 ?
                            (long) Math.pow(2, Byte.SIZE) + aByte :
                            aByte));
                }
            }
            return result.toString();
        } catch (Exception e) {
            logger.error("Error during converting file name to UTF-8 -> using source file name", e);
            return filename;
        }
    }
}
