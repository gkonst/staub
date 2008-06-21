package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.util.Base64;
import ru.spbspu.staub.data.question.*;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.util.JAXBUtil;
import ru.spbspu.staub.util.NonClosableInputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class QuestionServiceBean extends GenericServiceBean<Question, Integer> implements QuestionService {
    @EJB
    private QuestionTraceService questionTraceService;

    @EJB
    private DisciplineService disciplineService;

    @EJB
    private CategoryService categoryService;

    @EJB
    private TopicService topicService;

    @EJB
    private DifficultyService difficultyService;

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

    public Question importQuestion(InputStream inputStream, User user) throws JAXBException {
        logger.debug("> importQuestion(InputStream=#0, User=#1)", inputStream, user);

        QuestionDataType questionData = JAXBUtil.parseXml(QuestionDataType.class,
                new NonClosableInputStream(inputStream));

        Difficulty difficulty = difficultyService.importDifficulty(questionData.getDifficultyData());
        Discipline discipline = disciplineService.importDiscipline(questionData.getDisciplineData());
        Category category = categoryService.importCategory(questionData.getCategoryData(), discipline);
        Topic topic = topicService.importTopic(questionData.getTopicData(), category);

        Question question = new Question();
        question.setDifficulty(difficulty);
        question.setTopic(topic);
        question.setName(questionData.getName());

        int limit = questionData.getTimeLimit();
        if (limit != -1) {
            question.setTimeLimit(limit);
        }

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
        logger.debug("< importQuestion(InputStream, User)");

        return result;
    }

    public void exportQuestion(Integer id, OutputStream outputStream) throws JAXBException, IOException {
        logger.debug("> exportQuestion(Integer=#0, OutputStream=#1)", id, outputStream);

        Question question = null;
        try {
            question = getEntityManager().find(Question.class, id);
        } catch (NoResultException e) {
            // do nothing
        }

        if (question != null) {
            QuestionDataType questionData = new QuestionDataType();

            Topic topic = question.getTopic();
            TopicDataType topicData = topicService.exportTopic(topic.getId());
            questionData.setTopicData(topicData);

            Category category = topic.getCategory();
            CategoryDataType categoryData = categoryService.exportCategory(category.getId());
            questionData.setCategoryData(categoryData);

            Discipline discipline = category.getDiscipline();
            DisciplineDataType disciplineData = disciplineService.exportDiscipline(discipline.getId());
            questionData.setDisciplineData(disciplineData);

            Difficulty difficulty = question.getDifficulty();
            DifficultyDataType difficultyData = difficultyService.exportDifficulty(difficulty.getId());
            questionData.setDifficultyData(difficultyData);

            questionData.setName(question.getName());

            Integer timeLimit = question.getTimeLimit();
            if (timeLimit != null) {
                questionData.setTimeLimit(timeLimit);
            } else {
                questionData.setTimeLimit(-1);
            }

            QuestionType qt = question.getDefinition();
            if (qt != null) {
                byte[] bytes = JAXBUtil.createXml(qt);
                String definition = Base64.encodeBytes(bytes);
                questionData.setDefinition(definition);
            } else {
                questionData.setDefinition("");
            }

            byte[] bytes = JAXBUtil.createXml(questionData);
            outputStream.write(bytes);
        }

        logger.debug("< exportQuestion(Integer, OutputStream)");
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
}
