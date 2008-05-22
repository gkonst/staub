package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.TopicService;

/**
 * Webbean for manipulating list data of <code>Topic</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("topicListBean")
@Scope(SESSION)
public class TopicListBean extends GenericListBean<Topic> {
    private static final long serialVersionUID = 6593447031990966851L;

    @In
    private TopicService topicService;

    private Category category;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        Category category = (Category) Contexts.getConversationContext().get(Category.class.getName());
        if (category != null) {
            this.category = category;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return topicService.findTopics(formProperties, category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            topicService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }


    public String doCreate() {
        Contexts.getConversationContext().set(Category.class.getName(), category);
        return super.doCreate();
    }
}
