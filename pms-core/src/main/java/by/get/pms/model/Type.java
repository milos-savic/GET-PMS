package by.get.pms.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@MappedSuperclass
public class Type<T extends Type> extends PersistentEntity {

	@Column(name = "code")
	private String code = "";

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return getName();
	}

}
