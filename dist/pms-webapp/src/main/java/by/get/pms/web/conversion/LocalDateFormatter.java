package by.get.pms.web.conversion;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Milos.Savic on 11/3/2016.
 */
public class LocalDateFormatter implements Formatter<LocalDate> {

	private DateTimeFormatter formatter;

	public LocalDateFormatter(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public LocalDate parse(String source, Locale locale) throws ParseException {
		if (source == null || source.isEmpty()) {
			return null;
		}

		return LocalDate.parse(source, formatter);
	}

	@Override
	public String print(LocalDate localDate, Locale locale) {
		return formatter.format(localDate);
	}
}
