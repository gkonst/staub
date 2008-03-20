package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.web.RequestParameter;

/**
 * Generic webbean implementation, which operates with modes.
 * Modes can be used for disabling enabling some functionay
 * and for various bean initializations.
 *
 * @author Konstantin Grigoriev
 * @see BeanMode
 */
public abstract class GenericModeBean extends GenericBean {
    /**
     * Injected new mode from request.
     */
    @RequestParameter("beanMode")
    private BeanMode beanModeFromRequest;
    /**
     * Defines mode of the bean behaviour.
     */
    private BeanMode beanMode;

    public void setBeanMode(BeanMode beanMode) {
        this.beanMode = beanMode;
    }

    public BeanMode getBeanMode() {
        return beanMode;
    }

    /**
     * Defines initialize action for every backing bean.
     * E.g. all list beans must retrieve first page before first page render phase.
     */
    public abstract void initBean();

    /**
     * Defines bean mode for the current bean.
     *
     * @return <code>true</code> if bean mode defined, <code>false</code> - otherwise
     */
    protected boolean isBeanModeDefined() {
        if (beanModeFromRequest != null) {
            logger.debug("Defining bean mode...#0", beanModeFromRequest);
            setBeanMode(beanModeFromRequest);
            logger.debug("Defining bean mode... OK");
            return true;
        } else {
            logger.debug("Bean behaviour not specified -> Using old bean state!");
            return false;
        }
    }

    /**
     * Determines is bean in CREATE_MODE or not.
     *
     * @return true of false
     */
    public boolean isCreateMode() {
        return BeanMode.CREATE_MODE.equals(beanMode);
    }

    /**
     * Determines is bean in EDIT_MODE or not.
     *
     * @return true of false
     */
    public boolean isEditMode() {
        return BeanMode.EDIT_MODE.equals(beanMode);
    }

    /**
     * Determines is bean in VIEW_MODE or not.
     *
     * @return true of false
     */
    public boolean isViewMode() {
        return BeanMode.VIEW_MODE.equals(beanMode);
    }
}
