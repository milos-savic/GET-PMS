package by.get.pms.model;

import by.get.pms.exception.StaleEntityException;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Base entity for all persistent entities in the system.
 *
 * Created by Milos.Savic on 10/12/2016.
 */

@MappedSuperclass
public class PersistentEntity implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id = -1L;

	@Version
	@Column(name = "version")
	private long version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long vers) {
		if (vers < version) {
			throw new StaleEntityException("Stale entity exception: " + this.toString());
		}
		this.version = vers;
	}

	public Serializable getIdentifier() {
		if (id == -1L) {
			return null;
		} else {
			return id;
		}
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (other == null) {
			return false;
		} else if (getClass().isAssignableFrom(other.getClass())) {
			if (getIdentifier() == null) {
				return false;
			} else {
				final PersistentEntity pe = (PersistentEntity) other;
				return getIdentifier().equals(pe.getIdentifier());
			}
		} else if (other instanceof HibernateProxy) {
			return other.equals(this);
		}
		return false;
	}

	public int hashCode() {
		if (getIdentifier() != null) {
			return getIdentifier().hashCode();
		}
		return super.hashCode();
	}
}
