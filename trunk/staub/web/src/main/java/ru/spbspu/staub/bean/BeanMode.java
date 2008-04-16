package ru.spbspu.staub.bean;

/**
 * Defines working regime for all Faces beans.
 * @author Ivan_Elistratov
 */
public enum BeanMode {
    /**
     * Defines create bean mode.
     * Used in case of creation new persistent model.
     */
    CREATE_MODE,
    /**
     * Defines view bean mode.
     * Used in case of restriction on some input fields
     * backing by the current bean or simply for the viewing data.
     */
    VIEW_MODE,
    /**
     * Defines edit bean mode.
     * Used in case of input data for the database recording or simply
     * for the editing data.
     */
    EDIT_MODE,
    /**
     * Defines refresh bean mode.
     * Used in case of refreshing lists or some other data with keeping old bean behaviour.
     */
    REFRESH_MODE
}

