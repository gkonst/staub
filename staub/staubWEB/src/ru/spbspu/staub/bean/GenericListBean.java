package ru.spbspu.staub.bean;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.faces.DataModels;

import javax.faces.model.DataModel;
import java.util.List;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericListBean<T> extends GenericBean {

    private DataModel dataModel;

    protected abstract List<T> getResultList();

    @Create
    public void initList() {
        logger.debug(">>> Constructing list bean...");
        dataModel = DataModels.instance().getDataModel(getResultList());
        logger.debug("<<< Constructing list bean...Ok");
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }
}
