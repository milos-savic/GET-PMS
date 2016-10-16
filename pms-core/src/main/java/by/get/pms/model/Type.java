package by.get.pms.model;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@MappedSuperclass
public class Type<T extends Type> extends PersistentEntity {

	@Column(name = "code", nullable = false, unique = true, length = 30)
	private String code = "";

	@Column(name = "name", nullable = false, length = 50)
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
        return "Type{" +
                "id= " + getId() + '\'' +
                "code= '" + code + '\'' +
                ", name= '" + name + '\'' +
                ", description= '" + description + '\'' +
                '}';
    }
}
