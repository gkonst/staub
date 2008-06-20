package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.util.Base64;
import ru.spbspu.staub.data.question.QuestionDataType;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.util.JAXBUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stateless EJB Service for manipulations with <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("questionService")
@AutoCreate
@Stateless
/*@EJBs(value = {
@EJB(name = "ejb/DisciplineService", beanInterface = DisciplineService.class, beanName = "DisciplineServiceBean"),
@EJB(name = "ejb/CategoryService", beanInterface = CategoryService.class, beanName = "CategoryServiceBean"),
@EJB(name = "ejb/TopicService", beanInterface = TopicService.class, beanName = "TopicServiceBean"),
@EJB(name = "ejb/DifficultyService", beanInterface = DifficultyService.class, beanName = "DifficultyServiceBean")})*/
public class QuestionServiceBean extends GenericServiceBean<Question, Integer> implements QuestionService {
    @EJB
    private QuestionTraceService questionTraceService;

/*    @Resource
    private javax.ejb.SessionContext ejbContext;*/


    public FormTable find(FormProperties formProperties, Discipline discipline, Category category, Topic topic,
                          Difficulty difficulty, Integer questionId) {
        logger.debug("> find(FormProperties=#0, Discipline=#1, Category=#2, Topic=#3, Difficulty=#4, Integer=#5)",
                formProperties, discipline, category, topic, difficulty, questionId);

        StringBuilder query = new StringBuilder();
        query.append("select q from Question q");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (questionId != null) {
            query.append(" where cast(q.id as string) like :questionId and");
            parameters.put("questionId", questionId.toString() + '%');
        } else if (topic != null) {
            query.append(" where q.topic = :topic and");
            parameters.put("topic", topic);
        } else if (category != null) {
            query.append(", Category c join c.topics t where q.topic = t and c = :category and");
            parameters.put("category", category);
        } else if (discipline != null) {
            query.append(", Discipline d join d.categories c join c.topics t where q.topic = t and d = :discipline and");
            parameters.put("discipline", discipline);
        } else {
            query.append(" where");
        }

        query.append(" q.active = true");

        if (difficulty != null) {
            query.append(" and q.difficulty = :difficulty");
            parameters.put("difficulty", difficulty);
        }

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< find(FormProperties, Discipline, Category, Topic, Difficulty, Integer)");

        return formTable;
    }

    public long count(Category category) {
        Query q = getEntityManager().createQuery("select count(q) from Category c join c.topics t, Question q where q.topic = t and c = :category");
        q.setParameter("category", category);
        return (Long) q.getSingleResult();
    }

    public long count(Difficulty difficulty) {
        Query q = getEntityManager().createQuery("select count(q) from Question q where q.difficulty = :difficulty");
        q.setParameter("difficulty", difficulty);
        return (Long) q.getSingleResult();
    }

    public long count(Discipline discipline) {
        Query q = getEntityManager().createQuery("select count(q) from Discipline d join d.categories c join c.topics t, Question q where q.topic = t and d = :discipline");
        q.setParameter("discipline", discipline);
        return (Long) q.getSingleResult();
    }

    public long count(Topic topic) {
        Query q = getEntityManager().createQuery("select count(q) from Question q where q.topic = :topic");
        q.setParameter("topic", topic);
        return (Long) q.getSingleResult();
    }

    public Question saveQuestion(Question question, User user) {
        if (question.getId() == null) {
            question.setActive(true);
            question.setCreatedBy(user.getUsername());
            question.setCreated(new Date());
        } else {
            question.setModifiedBy(user.getUsername());
            question.setModified(new Date());
        }
        return makePersistent(question);
    }

    public Question importQuestion(QuestionDataType questionData, User user) throws JAXBException {
        logger.debug("> importQuestion(QuestionDataType=#0, User=#1)", questionData, user);

/*        Difficulty difficulty = getDifficultyService().importDifficulty(questionData.getDifficultyData());
        Discipline discipline = getDisciplineService().importDiscipline(questionData.getDisciplineData());
        Category category = getCategoryService().importCategory(questionData.getCategoryData(), discipline);
        Topic topic = getTopicService().importTopic(questionData.getTopicData(), category);*/

        Question question = new Question();
/*        question.setDifficulty(difficulty);
        question.setTopic(topic);*/
        question.setName(questionData.getName());
        question.setTimeLimit(questionData.getTimeLimit());

        QuestionType qt = null;
        String definition = questionData.getDefinition();
        if ((definition != null) && (definition.length() > 0)) {
            byte[] bytes = Base64.decode(definition);
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            qt = JAXBUtil.parseXml(QuestionType.class, stream);
        }
        question.setDefinition(qt);

        Question result = saveQuestion(question, user);

        logger.debug("*  Result: #0", result);
        logger.debug("< importQuestion(QuestionDataType, User)");

        return result;
    }

    public QuestionDataType exportQuestion(Integer id) throws JAXBException {
        logger.debug("> exportQuestion(Integer=#0)", id);

        Question question = null;
        try {
            question = getEntityManager().find(Question.class, id);
        } catch (NoResultException e) {
            // do nothing
        }

        QuestionDataType result = null;
        if (question != null) {
            result = new QuestionDataType();

/*            Topic topic = question.getTopic();
            TopicDataType topicData = getTopicService().exportTopic(topic.getId());
            result.setTopicData(topicData);

            Category category = topic.getCategory();
            CategoryDataType categoryData = getCategoryService().exportCategory(category.getId());
            result.setCategoryData(categoryData);

            Discipline discipline = category.getDiscipline();
            DisciplineDataType disciplineData = getDisciplineService().exportDiscipline(discipline.getId());
            result.setDisciplineData(disciplineData);

            Difficulty difficulty = question.getDifficulty();
            DifficultyDataType difficultyData = getDifficultyService().exportDifficulty(difficulty.getId());
            result.setDifficultyData(difficultyData);*/

            result.setName(question.getName());

            result.setTimeLimit(question.getTimeLimit());

            QuestionType qt = question.getDefinition();
            if (qt != null) {
                byte[] bytes = JAXBUtil.createXml(qt);
                String definition = Base64.encodeBytes(bytes);
                result.setDefinition(definition);
            } else {
                result.setDefinition("");
            }
        }

        logger.debug("*  Result: #0", result);
        logger.debug("< exportQuestion(Integer)");

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> findAll() {
        logger.debug(">>> Finding all(entity=#0)...", Question.class.getName());
        StringBuilder queryString = new StringBuilder().append("select q from Question q where q.active = true");
        List<Question> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    @Override
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder().append("select q from Question q where q.active = true");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    @Override
    public void remove(Question question) {
        logger.debug("> remove(Question=#0)", question);

        Question q = getEntityManager().merge(question);
        if (questionTraceService.count(q) == 0) {
            logger.debug("*  No related QuestionTrace entities found.");
            getEntityManager().remove(q);
            logger.debug("*  Question removed from a database.");
        } else {
            logger.debug("*  Related QuestionTrace entities exist.");
            q.setActive(false);
            getEntityManager().merge(q);
            logger.debug("*  Question marked inactive.");
        }

        logger.debug("< remove(Question)");
    }

/*    private DisciplineService getDisciplineService() {
        return (DisciplineService) ejbContext.lookup("ejb/DisciplineService");
    }

    private CategoryService getCategoryService() {
        return (CategoryService) ejbContext.lookup("ejb/CategoryService");
    }

    private TopicService getTopicService() {
        return (TopicService) ejbContext.lookup("ejb/TopicService");
    }

    private DifficultyService getDifficultyService() {
        return (DifficultyService) ejbContext.lookup("ejb/DifficultyService");
    }*/
}
