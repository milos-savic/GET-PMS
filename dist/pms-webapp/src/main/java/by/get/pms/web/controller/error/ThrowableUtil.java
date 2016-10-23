package by.get.pms.web.controller.error;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Milos.Savic on 10/6/2016.
 */
public class ThrowableUtil {

	public static final String EMPTY = "";

	public static java.lang.Throwable getRootCause(java.lang.Throwable throwable) {
		java.lang.Throwable cause;
		while ((cause = throwable.getCause()) != null) {
			throwable = cause;
		}
		return throwable;
	}

	public static String getStackTrace(java.lang.Throwable throwable) {
		if (throwable == null) {
			return EMPTY;
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}
}
