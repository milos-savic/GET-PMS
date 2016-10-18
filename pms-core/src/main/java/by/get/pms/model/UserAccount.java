package by.get.pms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Entity
@Table(name = "useraccount")
public class UserAccount extends PersistentEntity {

    @Column(name = "username", unique = true, nullable = false, length = 30)
    private String username;

    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "active")
    private Boolean active;

    @OneToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role")
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime startDate) {
        this.creationDate = startDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean enabled) {
        this.active = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Serializable getIdentifier() {
        return username;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id= " + getId() + '\'' +
                "username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", active=" + active +
                '}';
    }
}
