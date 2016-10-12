package by.get.pms.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@MappedSuperclass
public class Type<T extends Type> extends PersistentEntity {

	@Column(name = "key_", unique = true)
	private String key;

	@Column(name = "name_", nullable = false)
	private String name;

	@Column(name = "description_")
	private String description;

	@Column(name = "code_")
	private String code = "";

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Serializable getIdentifier() {
		return key;
	}

	@Override
	public String toString() {
		return getName();
	}

}
