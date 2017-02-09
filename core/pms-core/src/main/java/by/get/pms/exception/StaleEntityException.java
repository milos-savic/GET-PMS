package by.get.pms.exception;

/**
 * Stale entity exception occurs when entity database version is not consistent
 * with entity version in memory.
 *
 * Created by Milos.Savic on 10/12/2016.
 */
public class StaleEntityException extends RuntimeException {

	public StaleEntityException(String message) {
		super(message);
	}
}
