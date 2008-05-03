package ru.spbspu.staub.bean.test;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.DifficultyWrapper;
import ru.spbspu.staub.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Webbean for manipulating detail data of <code>Test</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testDetailBean")
@Scope(SESSION)
public class TestDetailBean extends GenericDetailBean<Test> {
    private static final long serialVersionUID = -6228085298741173166L;

    @In
    private User user;

    @In
    private TestService testService;

    @In
    private DisciplineService disciplineService;

    @In
    private CategoryService categoryService;

    @In
    private TopicService topicService;

    @In
    private DifficultyService difficultyService;

    private List<Discipline> disciplineList;

    private List<Category> categoryList;

    private List<Topic> topicList;

    private List<DifficultyWrapper> difficultyList;

    private Discipline discipline;

    private Topic[] selectedTopics;

    @Override
    protected void fillModel(Integer modelId) {
        fillDisciplines();
        fillDifficulties();
        cleanTopics();
        if (isCreateMode()) {
            setModel(new Test());
            setDiscipline((Discipline) Contexts.getConversationContext().get(Discipline.class.getName()));
            getModel().setCategory((Category) Contexts.getConversationContext().get(Category.class.getName()));
            if (getModel().getCategory() != null) {
                fillTopics();
            }
            Topic topic = (Topic) Contexts.getConversationContext().get(Topic.class.getName());
            if (topic != null) {
                selectedTopics = new Topic[]{topic};
            }
        } else {
            setModel(testService.findById(modelId));
            setDiscipline(getModel().getCategory().getDiscipline());
            fillTopics();
            if (getModel().getTopics() != null && !getModel().getTopics().isEmpty()) {
                selectedTopics = getModel().getTopics().toArray(new Topic[getModel().getTopics().size()]);
            }
            if (getModel().getDifficultyLevels() != null && !getModel().getDifficultyLevels().isEmpty()) {
                for (TestDifficulty td : getModel().getDifficultyLevels()) {
                    for (DifficultyWrapper dw : difficultyList) {
                        if (td.getDifficulty().equals(dw.getDifficulty())) {
                            dw.setSelected(Boolean.TRUE);
                            dw.setPassScore(td.getPassScore());
                            dw.setQuestionsCount(td.getQuestionsCount());
                        }
                    }
                }
            }
        }
        refreshCategories();
    }

    @Override
    public void doSave() {
        logger.debug("Saving test...");
        if (selectedTopics != null && selectedTopics.length != 0) {
            if (getModel().getTopics() == null) {
                getModel().setTopics(new HashSet<Topic>());
            }
            // may be bad but works fine
            getModel().getTopics().clear();
            getModel().getTopics().addAll(Arrays.asList(selectedTopics));
        }
        if (isDifficultiesEmpty()) {
            addFacesMessageFromResourceBundle("test.detail.required.difficulty");
            logger.debug("Saving... failed");
            return;
        }
        setModel(testService.saveTest(getModel(), difficultyList, user));
        logger.debug("  Changing bean mode -> #0", BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    private void fillDisciplines() {
        disciplineList = disciplineService.findAll();
    }

    public void refreshCategories() {
        if (discipline != null) {
            categoryList = categoryService.findCategories(discipline);
        } else {
            categoryList = null;
        }
    }

    public void refreshTopics() {
        if (getModel().getCategory() != null) {
            fillTopics();
        } else {
            cleanTopics();
        }
    }

    private void fillTopics() {
        topicList = topicService.findTopics(getModel().getCategory());
        selectedTopics = new Topic[topicList.size()];
    }

    private void cleanTopics() {
        topicList = null;
        selectedTopics = null;
    }

    private void fillDifficulties() {
        List<Difficulty> difficulties = difficultyService.findAll();
        difficultyList = new ArrayList<DifficultyWrapper>();
        for (Difficulty difficulty : difficulties) {
            difficultyList.add(new DifficultyWrapper(difficulty));
        }
    }

    private boolean isDifficultiesEmpty() {
        for (DifficultyWrapper df : difficultyList) {
            if (df.getSelected()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTopicsAvailable() {
        return topicList != null && !topicList.isEmpty();
    }

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public List<DifficultyWrapper> getDifficultyList() {
        return difficultyList;
    }

    public void setDifficultyList(List<DifficultyWrapper> difficultyList) {
        this.difficultyList = difficultyList;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Topic[] getSelectedTopics() {
        return selectedTopics;
    }

    public void setSelectedTopics(Topic[] selectedTopics) {
        this.selectedTopics = selectedTopics;
    }
}