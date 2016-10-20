package by.get.pms.web.response;

/**
 * Created by Milos.Savic on 10/6/2016.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder pattern implementation for building {@link Response} objects.
 */
public class ResponseBuilder {

    private final static Logger logger = LoggerFactory.getLogger(ResponseBuilder.class);

    private Map<String, Object> model;

    private List<Message> messages;

    private List<FieldError> fieldErrors;

    private boolean success;

    private MessageSource messageSource;

    ResponseBuilder(MessageSource messageSource) {
        super();

        this.messageSource = messageSource;

        model = new HashMap<>();
        messages = new ArrayList<>();
        fieldErrors = new ArrayList<>();
        success = true;
    }

    public ResponseBuilder addObject(final String key, final Object object) {
        model.put(key, object);
        return this;
    }

    public ResponseBuilder addSuccessMessage(final String messageKey, final Object... messageParams) {
        return getResponseBuilder(messageKey, messageParams, MessageType.SUCCESS);
    }

    public ResponseBuilder addInfoMessage(final String messageKey, final Object... messageParams) {
        return getResponseBuilder(messageKey, messageParams, MessageType.INFO);
    }

    private ResponseBuilder getResponseBuilder(String messageKey, Object[] messageParams, MessageType messageType) {
        final String msg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        messages.add(new Message(messageType, msg));
        return this;
    }

    public ResponseBuilder addErrorMessage(final String messageKey, final Object... messageParams) {
        String msg = buildMessage(messageKey, messageParams);
        return addErrorMessage(msg);
    }

    /**
     * Adds an error message that is linked to a {@link Throwable} to the response.
     *
     * @param throwable     The {@link Throwable} that should be reported in the response. The message
     *                      that will be displayed is being looked up in the {@link MessageSource} with the
     *                      {@code throwable}'s class being used as the key.
     * @param messageParams Optional message parameters.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     */
    public ResponseBuilder addError(final Throwable throwable, final Object... messageParams) {
        String msg = buildMessage(throwable.getClass().getName(), messageParams);
        return addErrorMessage(msg);
    }

    private String buildMessage(String messageKey, Object[] messageParams) {
        String msg;
        try {
            msg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            logger.error("Error during getting message with key: {}, params: {}, locale: {}", messageKey, messageParams,
                    LocaleContextHolder.getLocale());
            msg = messageSource.getMessage("unknown.error", messageParams, LocaleContextHolder.getLocale());
        }
        return msg;
    }

    private ResponseBuilder addErrorMessage(String msg) {
        messages.add(new Message(MessageType.ERROR, msg));
        success = false;
        return this;
    }

    /**
     * Convenient method for adding {@link Error}-s which occurred during the <i>binding</i> and
     * <i>validation</i> process in a Spring {@link Controller} method.
     *
     * @param errors The errors which have occurred.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     */
    public ResponseBuilder addErrors(final Errors errors) {
        addGlobalErrors(errors);
        addFieldErrors(errors);
        success = errors == null || (!errors.hasErrors() && !errors.hasFieldErrors());
        return this;
    }

    private void addGlobalErrors(final Errors errors) {
        for (final ObjectError springGlobalError : errors.getGlobalErrors()) {
            final String defaultMsg = springGlobalError.getDefaultMessage();
            final String customMsg = findCustomMessage(springGlobalError);
            messages.add(new Message(MessageType.ERROR, customMsg != null ? customMsg : defaultMsg));
        }
    }

    private void addFieldErrors(final Errors errors) {
        for (final org.springframework.validation.FieldError springFieldError : errors.getFieldErrors()) {

            final String defaultMsg = springFieldError.getDefaultMessage();
            final String customMsg = findCustomMessage(springFieldError);
            final FieldError fieldError = new FieldError(springFieldError.getField(),
                    customMsg != null ? customMsg : defaultMsg);
            fieldErrors.add(fieldError);
        }
    }

    private String findCustomMessage(final ObjectError error) {
        for (String code : error.getCodes()) {
            final String codeMsg = messageSource
                    .getMessage(code, error.getArguments(), null, LocaleContextHolder.getLocale());
            if (codeMsg != null) {
                return codeMsg;
            }
        }

        return null;
    }

    public ResponseBuilder addFieldError(final String fieldName, final String messageKey,
                                         final String... messageParams) {
        final String codeMsg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        final FieldError fieldError = new FieldError(fieldName, codeMsg);
        fieldErrors.add(fieldError);
        success = false;
        return this;
    }

    public ResponseBuilder indicateSuccess() {
        success = true;
        return this;
    }

    /**
     * Builds a {@link Response} object.
     */
    public Response build() {
        final Response response = new Response();
        response.setModel(model);
        response.setMessages(messages);
        response.setFieldErrors(fieldErrors);
        response.setSuccess(success);
        return response;
    }

}
