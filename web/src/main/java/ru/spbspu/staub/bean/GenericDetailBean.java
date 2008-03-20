package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.web.RequestParameter;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericDetailBean<T> extends GenericModeBean {

    @RequestParameter
    private Integer modelId;

    private T model;

    /**
     * Fills model on base of beanMode and model identifier.
     * E.g. initializes model with new entity instance or fetches
     * specific entity from db by model identifier.
     *
     * @param modelId model identifier
     */
    protected abstract void fillModel(Integer modelId);

    /**
     * {@inheritDoc}
     */
    @Override
    public void initBean() {
        if (isBeanModeDefined()) {
            logger.debug(">>> Initializating detail bean...id : #0", modelId);
            fillModel(modelId);
            logger.debug("<<< Initializating detail bean...Ok");
        }
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
