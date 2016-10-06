package by.get.pms.web.response;

import java.util.List;
import java.util.Map;

/**
 * Created by Milos.Savic on 10/6/2016.
 */

/**
 * Response object to be returned by controller methods as a serialized object in the response body.
 */
public class Response {

	private Map<String, Object> model;
	private List<Message> messages;
	private List<FieldError> fieldErrors;
	private boolean success = true;

	Response() {
		super();
	}

	public Map<String, Object> getModel() {
		return model;
	}

	void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
