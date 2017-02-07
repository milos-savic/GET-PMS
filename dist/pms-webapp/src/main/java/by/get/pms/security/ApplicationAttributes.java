package by.get.pms.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Milos.Savic on 10/12/2016.
 */
public class ApplicationAttributes implements Serializable {

	private Map<String, Object> storage = new HashMap<>();

	public void clearAll() {
		storage.clear();
	}

	public void put(String key, Object value) {
		this.storage.put(key, value);
	}

	public Object get(String key) {
		return storage.get(key);
	}
}
