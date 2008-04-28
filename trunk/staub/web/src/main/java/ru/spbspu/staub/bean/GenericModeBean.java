package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.In;
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
    private static final long serialVersionUID = -4271552460137044825L;

    protected static final String BEAN_MODE = "beanMode";
    /**
     * Injected new mode from request parameter.
     */
    @In(value = BEAN_MODE, required = false)
    @RequestParameter(BEAN_MODE)
    private BeanMode beanModeFromRequestParameter;
    /**
     * Injected new mode from request attribute.
     */
    @In(value = BEAN_MODE, required = false)
    private BeanMode beanModeFromRequestAttribute;
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
        if (beanModeFromRequestParameter != null) {
            logger.debug("Defining bean mode from request parameter...#0", beanModeFromRequestParameter);
            setBeanMode(beanModeFromRequestParameter);
            logger.debug("Defining bean mode... OK");
            return true;
        } else if (beanModeFromRequestAttribute != null) {
            logger.debug("Defining bean mode from request attribute...#0", beanModeFromRequestAttribute);
            setBeanMode(beanModeFromRequestAttribute);
            logger.debug("Defining bean mode... OK");
            return true;
        } else {
            logger.debug("Bean behaviour not specified -> Using old bean state!");
            return false;
        }
    }

    /**
     * Sets view mode for the destination bean and
     * redirects to the destination page.
     *
     * @param outcome string for navigation
     *
     * @return string for navigation
     */
    public String doView(String outcome) {
        getRequest().setAttribute(BEAN_MODE, BeanMode.VIEW_MODE);
        return outcome;
    }

    /**
     * Sets edit mode for the destination bean and
     * redirects to the destination page.
     *
     * @param outcome string for navigation
     *
     * @return string for navigation
     */
    public String doEdit(String outcome) {
        getRequest().setAttribute(BEAN_MODE, BeanMode.EDIT_MODE);
        return outcome;
    }

    /**
     * Sets create mode for the destination bean and
     * redirects to the destination page.
     *
     * @param outcome string for navigation
     *
     * @return string for navigation
     */
    public String doCreate(String outcome) {
        getRequest().setAttribute(BEAN_MODE, BeanMode.CREATE_MODE);
        return outcome;
    }

    /**
     * Sets refresh mode for the destination bean and
     * redirects to the destination page.
     *
     * @param outcome string for navigation
     *
     * @return string for navigation
     */
    public String doRefresh(String outcome) {
        getRequest().setAttribute(BEAN_MODE, BeanMode.REFRESH_MODE);
        return outcome;
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
