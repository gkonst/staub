package ru.spbspu.staub.entity;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import ru.spbspu.staub.model.question.QuestionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The <code>Question</code> class represents the Question entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(name = "question", schema = "staub")
@org.hibernate.annotations.TypeDef(name = "question_type",
        typeClass = ru.spbspu.staub.entity.XmlType.class,
        parameters = {@Parameter(name = "pojoClass", value = "ru.spbspu.staub.model.question.QuestionType")}
)
public class Question implements Serializable {
    private static final long serialVersionUID = 8070548991033139442L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "QuestionIdGenerator", sequenceName = "seq_question", allocationSize = 1)
    @GeneratedValue(generator = "QuestionIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Category category;

    @ManyToOne
    @JoinColumn(name = "fk_category", referencedColumnName = "id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "fk_difficulty", referencedColumnName = "id", nullable = false)
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    private Discipline discipline;

    @ManyToOne
    @JoinColumn(name = "fk_discipline", referencedColumnName = "id", nullable = false)
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    private String name;

    @Basic
    @Column(name = "name", length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer timeLimit;

    @Basic
    @Column(name = "time_limit", length = 10)
    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    private QuestionType definition;

    @Column(name = "definition")
    @Type(type = "question_type")
    public QuestionType getDefinition() {
        return definition;
    }

    public void setDefinition(QuestionType definition) {
        this.definition = definition;
    }

    private Date created;

    @Basic
    @Column(name = "created", nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private String createdBy;

    @Basic
    @Column(name = "created_by", length = 64, nullable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private Date modified;

    @Basic
    @Column(name = "modified")
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    private String modifiedBy;

    @Basic
    @Column(name = "modified_by", length = 64)
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Question)) {
            return false;
        }

        Question other = (Question) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", timeLimit=").append(timeLimit);
//        sb.append(", definition='").append(definition).append('\'');
        sb.append(", created=").append(created);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", modified=").append(modified);
        sb.append(", modifiedBy='").append(modifiedBy).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
