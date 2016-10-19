package by.get.pms.dto;

import java.io.Serializable;

/**
 * Created by Milos.Savic on 10/17/2016.
 */
public class DTO implements Serializable {

    private Long id;

    public DTO() {
    }

    public DTO(long id) {
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
                final DTO dto = (DTO) other;
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
