package by.get.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by milos on 14-Oct-16.
 */

@Entity
@Table(name = "project")
public class Project extends PersistentEntity {

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Serializable getIdentifier() {
        return getCode();
    }

    @Override
    public String toString() {
        return "Project{" +
                "id= " + getId() + '\'' +
                "code= '" + code + '\'' +
                ", name= '" + name + '\'' +
                ", description= '" + description + '\'' +
                '}';
    }
}
