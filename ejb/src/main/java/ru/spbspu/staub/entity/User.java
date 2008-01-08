package ru.spbspu.staub.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The <code>User</code> class represents the User entity.
 *
 * @author Alexander V. Elagin
 */
@Entity
@Table(name = "\"user\"", schema = "staub")
public class User implements Serializable {
    private static final long serialVersionUID = 4840352094869214259L;

    private Integer id;

    @Id
    @SequenceGenerator(name = "UserIdGenerator", sequenceName = "seq_user", allocationSize = 1)
    @GeneratedValue(generator = "UserIdGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, length = 10)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String username;

    @Basic
    @Column(name = "username", nullable = false, length = 64)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String password;

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof User)) {
            return false;
        }

        User other = (User) otherObject;

        return id.equals(other.id);
    }

    @Override    
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append("{id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
