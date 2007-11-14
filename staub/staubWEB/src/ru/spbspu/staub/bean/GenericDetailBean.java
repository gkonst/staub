package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.web.RequestParameter;

import java.io.Serializable;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericDetailBean<T> extends GenericBean {

    @RequestParameter
    private Long modelId;

    private T model;

    protected abstract void fillModel(Long modelId);

    @Create
    public void initDetailBean() {
        logger.debug("--> Initializating detail bean...id : #0", modelId);
        fillModel(modelId);
        logger.debug("<-- Initializating detail bean...Ok");
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
