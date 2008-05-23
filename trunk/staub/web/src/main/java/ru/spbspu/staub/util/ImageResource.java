package ru.spbspu.staub.util;

import static org.jboss.seam.ScopeType.APPLICATION;
import org.jboss.seam.annotations.*;
import static org.jboss.seam.annotations.Install.FRAMEWORK;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;
import org.jboss.seam.servlet.ContextualHttpServletRequest;
import org.jboss.seam.web.AbstractResource;

import javax.activation.MimetypesFileTypeMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Seam component for reading graphic files from disk by name.
 *
 * @author Konstantin Grigoriev
 */
@Scope(APPLICATION)
@Name("ru.spbspu.staub.imageResource")
@Install(precedence = FRAMEWORK)
@BypassInterceptors
@Startup
public class ImageResource extends AbstractResource {

    @Logger
    private Log logger;

    public static final String RESOURCE_PATH = "/loadImage";

    private static final String UPLOAD_RESOURCE_DIR_ENV = "upload-resource-directory";

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

    private void doWork(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo().substring(getResourcePath().length() + 1);
        File file = new File(getResourceDirectory() + File.separator + pathInfo);
        if (file.exists()) {
            // TODO may be bad
            MimetypesFileTypeMap mimeType = new MimetypesFileTypeMap();
            String contentType = mimeType.getContentType(file);
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentLength((int) file.length());
            FileInputStream fi = null;
            try {
                ServletOutputStream os = response.getOutputStream();
                fi = new FileInputStream(file);
                byte[] buf = new byte[100];
                int size;
                while ((size = fi.read(buf)) > 0) {
                    os.write(buf, 0, size);
                }
                os.flush();
            } finally {
                if (fi != null) {
                    fi.close();
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static String getResourceDirectory() {
        try {
            Context iniCtx = new InitialContext();
            Context compEnv = (Context) iniCtx.lookup("java:comp/env");
            return String.valueOf(compEnv.lookup(UPLOAD_RESOURCE_DIR_ENV));
        } catch (NamingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Create
    public void initResource() {
        String uploadDir = getResourceDirectory();
        logger.info("Initializing image resource for directory : #0", uploadDir);
        File file = new File(getResourceDirectory());
        if(!file.exists() || !file.isDirectory()) {
            logger.error("Upload resource directory not found or not a directory");
        }
    }
}
