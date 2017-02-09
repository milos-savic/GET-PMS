package by.get.pms.web.response;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Created by Milos.Savic on 10/6/2016.
 */
@Component
public class ResponseBuilderFactoryBean implements FactoryBean<ResponseBuilder> {

	@Autowired
	private MessageSource messageSource;

	@Override
	public ResponseBuilder getObject() throws Exception {
		return new ResponseBuilder(messageSource);
	}

	@Override
	public Class<?> getObjectType() {
		return ResponseBuilder.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public ResponseBuilder instance() {

		try {
			return getObject();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
