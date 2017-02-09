package by.get.pms.web.response;

/**
 * Created by Milos.Savic on 10/6/2016.
 */

import com.google.common.base.Objects;

/**
 * Represents a message that will be displayed to the user. It supports several different types as
 * defined in {@link MessageType}.
 */

public class Message {

	private final MessageType type;

	private final String message;

	public Message(MessageType type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Message message1 = (Message) o;
		return type == message1.type && Objects.equal(message, message1.message);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(type, message);
	}
}
