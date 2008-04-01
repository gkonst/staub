package ru.spbspu.staub.util;

import static org.jboss.seam.ScopeType.APPLICATION;
import org.jboss.seam.annotations.Install;
import static org.jboss.seam.annotations.Install.BUILT_IN;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
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
@Name("myImageResource")
@Install(precedence = BUILT_IN)
@BypassInterceptors
public class ImageResource extends AbstractResource {

    private static final String RESOURCE_PATH = "/loadImage";

    private static final String RESOURCE_DIR_ENV = "resource-directory";

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
            FileInputStream fi = new FileInputStream(file);
            byte[] buf = new byte[fi.available()];
            fi.read(buf);
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentLength(buf.length);
            ServletOutputStream os = response.getOutputStream();
            os.write(buf);
            os.flush();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static String getResourceDirectory() {
        try {
            Context iniCtx = new InitialContext();
            Context compEnv = (Context) iniCtx.lookup("java:comp/env");
            return String.valueOf(compEnv.lookup(RESOURCE_DIR_ENV));
        } catch (NamingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
