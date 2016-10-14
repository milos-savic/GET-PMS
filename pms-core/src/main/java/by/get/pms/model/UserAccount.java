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

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "active", nullable = false)
    private Character active;

    @OneToOne
    @JoinColumn(name = "user", unique = true, nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Role.class)
    @JoinTable(name = "userrole",
            joinColumns = @JoinColumn(name = "useraccount"),
            inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Role> roles = new HashSet<>();

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

    public Character getActive() {
        return active;
    }

    public void setActive(Character enabled) {
        this.active = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
