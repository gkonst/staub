package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.log.Log;

/**
 * Generic webbean implementation used in presentation layer.
 * @author Konstantin Grigoriev
 */
public abstract class GenericBean {
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
}
