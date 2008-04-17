package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The <code>Test</code> class represents the Test entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(name = "test", schema = "staub")
public class Test implements Serializable {
    private static final long serialVersionUID = -4826754008513352835L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "TestIdGenerator", sequenceName = "seq_test", allocationSize = 1)
    @GeneratedValue(generator = "TestIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    private String description;

    @Basic
    @Lob
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    private Integer passScore;

    @Basic
    @Column(name = "pass_score", length = 10, nullable = false)
    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    private Boolean active;

    @Basic
    @Column(name = "active", length = 1)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Category category;

    @OneToOne
    @JoinColumn(name = "fk_category", referencedColumnName = "id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private List<Topic> topicList;

    @OneToMany
    @JoinTable(schema = "staub", name = "test_topic",
            joinColumns = @JoinColumn(name = "fk_test", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "fk_topic", referencedColumnName = "id", nullable = false))
    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
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
        if (!(otherObject instanceof Test)) {
            return false;
        }

        Test other = (Test) otherObject;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", timeLimit=").append(timeLimit);
        sb.append(", passScore=").append(passScore);
        sb.append(", created=").append(created);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", modified=").append(modified);
        sb.append(", modifiedBy='").append(modifiedBy).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
