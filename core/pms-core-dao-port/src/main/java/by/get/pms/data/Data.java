package by.get.pms.data;

import java.io.Serializable;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public class Data implements Serializable {

	private Long id;

	public Data() {
	}

	public Data(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	protected Serializable getBusinessIdentifier() {
		return id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (other == null) {
			return false;
		} else if (getClass().isAssignableFrom(other.getClass())) {
			if (getBusinessIdentifier() == null) {
				return false;
			} else {
				final Data dto = (Data) other;
				return getBusinessIdentifier().equals(dto.getBusinessIdentifier());
			}
		}
		return false;
	}

	public int hashCode() {
		if (getBusinessIdentifier() != null) {
			return getBusinessIdentifier().hashCode();
		}
		return super.hashCode();
	}
}
