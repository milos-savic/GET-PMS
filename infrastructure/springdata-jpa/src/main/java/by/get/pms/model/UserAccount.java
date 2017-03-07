package by.get.pms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Entity
@Table(name = "useraccount")
public class UserAccount extends PersistentEntity {

    @Column(name = "username", unique = true, nullable = false, length = 30)
    private String userName;

    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "active")
    private Boolean active;

    @OneToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserAccount() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime startDate) {
        this.creationDateTime = startDate;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public Serializable getIdentifier() {
        return userName;
    }

    @Override
    public String toString() {
        return "UserAccount{" + "id= " + getId() + '\'' + "userName='" + userName + '\'' + ", creationDateTime="
                + creationDateTime + ", active=" + active + '}';
    }
}
