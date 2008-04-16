package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>TestTopic</code> class represents the TestTopic entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "test_topic")
@IdClass(ru.spbspu.staub.entity.TestTopicPK.class)
public class TestTopic implements Serializable {
    private static final long serialVersionUID = 6194446013046368364L;

    private Integer fkTest;

    @Id
    @Column(name = "fk_test", nullable = false, length = 10)
    public Integer getFkTest() {
        return fkTest;
    }

    public void setFkTest(Integer fkTest) {
        this.fkTest = fkTest;
    }

    private Integer fkTopic;

    @Id
    @Column(name = "fk_topic", nullable = false, length = 10)
    public Integer getFkTopic() {
        return fkTopic;
    }

    public void setFkTopic(Integer fkTopic) {
        this.fkTopic = fkTopic;
    }
}
