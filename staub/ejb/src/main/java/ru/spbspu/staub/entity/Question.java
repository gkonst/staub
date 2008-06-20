package ru.spbspu.staub.entity;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
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
@TypeDef(name = "question_type",
        typeClass = XmlType.class,
        parameters = {@Parameter(name = "pojoClass", value = "ru.spbspu.staub.model.question.QuestionType")}
)
public class Question implements Serializable {
    private static final long serialVersionUID = 8070548991033139442L;

    private Integer id;

    private Difficulty difficulty;

    private String name;

    private Integer timeLimit;

    private QuestionType definition;

    private Boolean active;

    private Topic topic;

    private Date created;

    private String createdBy;

    private Date modified;

    private String modifiedBy;

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

    @ManyToOne
    @JoinColumn(name = "fk_difficulty", referencedColumnName = "id", nullable = false)
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Basic
    @Column(name = "name", length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "time_limit", length = 10)
    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Column(name = "definition")
    @Type(type = "question_type")
    public QuestionType getDefinition() {
        return definition;
    }

    public void setDefinition(QuestionType definition) {
        this.definition = definition;
    }

    @Basic
    @Column(name = "active", length = 1)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @OneToOne
    @JoinColumn(name = "fk_topic", referencedColumnName = "id", nullable = false)
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Basic
    @Column(name = "created_by", length = 64, nullable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

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
