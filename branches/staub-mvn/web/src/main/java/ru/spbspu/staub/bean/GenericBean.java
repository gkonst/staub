package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Generic webbean implementation used in presentation layer.
 * @author Konstantin Grigoriev
 */
public abstract class GenericBean implements Serializable {
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

    protected String getSessionId() {
        return ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getId();
    }

    protected String getCurrentUser() {
        return Identity.instance().getUsername();
    }
}
