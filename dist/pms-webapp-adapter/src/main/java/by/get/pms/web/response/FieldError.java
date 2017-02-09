package by.get.pms.web.response;

import com.google.common.base.Objects;

/**
 * Created by Milos.Savic on 10/6/2016.
 */
public class FieldError {

	private String field;

	private String message;

	public FieldError() {
		super();
	}

	public FieldError(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FieldError that = (FieldError) o;
		return Objects.equal(field, that.field) && Objects.equal(message, that.message);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(field, message);
	}
}
