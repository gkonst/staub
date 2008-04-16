package ru.spbspu.staub.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The <code>TestTopicPK</code> class represents a primary key of the {@link TestTopic} entity.
 *
 * @author Alexander V. Elagin
 */
public class TestTopicPK implements Serializable {
    private static final long serialVersionUID = -4520114758068080597L;
    
    private Integer fkTest;

    @Column(name = "fk_test", nullable = false, length = 10)
    @Id
    public Integer getFkTest() {
        return fkTest;
    }

    public void setFkTest(Integer fkTest) {
        this.fkTest = fkTest;
    }

    private Integer fkTopic;

    @Column(name = "fk_topic", nullable = false, length = 10)
    @Id
    public Integer getFkTopic() {
        return fkTopic;
    }

    public void setFkTopic(Integer fkTopic) {
        this.fkTopic = fkTopic;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestTopicPK that = (TestTopicPK) o;

        return (fkTest != null ? fkTest.equals(that.fkTest) : that.fkTest == null)
                && (fkTopic != null ? fkTopic.equals(that.fkTopic) : that.fkTopic == null);

    }

    public int hashCode() {
        int result;
        result = (fkTest != null ? fkTest.hashCode() : 0);
        result = 31 * result + (fkTopic != null ? fkTopic.hashCode() : 0);
        return result;
    }
}
