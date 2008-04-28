package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.web.RequestParameter;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericDetailBean<T> extends GenericModeBean {
    private static final long serialVersionUID = -3291096277306979986L;

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

    /**
     * Defines save operation for current bean (optional).
     */
    public void doSave() {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    /**
     * Defines cancel operation for current bean (optional).
     *
     * @return navigation outcome, by default 'list'
     */
    public String doCancel() {
        return "list";
    }

    /**
     * Defines back operation for current bean (optional).
     *
     * @return navigation outcome, by default 'list'
     */
    public String doBack() {
        return doRefresh("list");
    }
}
