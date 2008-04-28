package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.log.Log;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Generic webbean implementation used in presentation layer.
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericBean implements Serializable {
    private static final long serialVersionUID = 6717563395858478703L;

    @Logger
    protected Log logger;

    protected FacesMessages getFacesMessages() {
        return FacesMessages.instance();
    }

    protected void addFacesMessage(String messageTemplate, Object... params) {
        getFacesMessages().add(messageTemplate, params);
    }

    protected void addFacesMessageFromResourceBundle(String key, Object... params) {
        getFacesMessages().addFromResourceBundle(key, params);
    }

    protected String render(String path) {
        return Renderer.instance().render(path);
    }

    /**
     * Returns current http servlet request.
     *
     * @return servlet request
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    protected String getSessionId() {
        return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getId();
    }
}
