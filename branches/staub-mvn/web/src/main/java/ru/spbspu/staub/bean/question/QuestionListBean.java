package ru.spbspu.staub.bean.question;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.QuestionService;

/**
 * TODO add description
 *
 * @author Konstantin Grigoriev
 */
@Name("questionListBean")
@Scope(SESSION)
public class QuestionListBean extends GenericListBean<Question> {
    @In
    private QuestionService questionService;


    protected FormTable findObjects(FormProperties formProperties) {
        return questionService.findAll(formProperties);
    }
}
