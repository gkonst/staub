package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>Student</code> class represents the Student entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(schema = "staub", name = "student")
public class Student implements Serializable {
    private static final long serialVersionUID = 2591120742273218659L;
    
    private Integer id;

    @Id
    @SequenceGenerator(name = "StudentIdGenerator", sequenceName = "seq_student", allocationSize = 1)
    @GeneratedValue(generator = "StudentIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    @Basic
    @Column(name = "name", nullable = false, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String code;

    @Basic
    @Column(name = "code", nullable = false, length = 16)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private Group group;

    @ManyToOne
    @JoinColumn(name = "fk_group", referencedColumnName = "id", nullable = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }

        Student student = (Student) o;

        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student");
        sb.append("{id=").append(id);
        sb.append(", group=").append(group);
        sb.append(", name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
