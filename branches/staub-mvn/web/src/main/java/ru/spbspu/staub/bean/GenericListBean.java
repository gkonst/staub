package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import java.util.List;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericListBean<T> extends GenericBean {

    @DataModel
    private List<T> dataModel;

    @DataModelSelection
    private T selected;

    protected abstract List<T> getResultList();

    @Factory("dataModel")
    public void fillList() {
        logger.debug(">>> Constructing list bean...");
        dataModel = getResultList();
        logger.debug("<<< Constructing list bean...Ok");
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }
}
