package org.thorntail.example.jaxrs.exception;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

	private static final Logger LOGGER = Logger.getLogger(PersistenceExceptionMapper.class);
	
	private static final String CODE_LABEL = "code";
	private static final String CODE_VALUE = "ERR-2021";
	private static final String TYPE_LABEL = "type";
	private static final String TYPE_VALUE = "DATABASE";
	private static final String MESSAGE_LABEL = "message";

	private Map<String, String> response;

	@PostConstruct
	public void init() {
		this.response = new HashMap<String, String>();
	}

	@Override
	public Response toResponse(PersistenceException exception) {
		LOGGER.debug("toResponse() -> " + exception.getClass());
		if (exception instanceof EntityNotFoundException) {
			return Response.status(Status.NOT_FOUND).build();
		}
		this.response.put(CODE_LABEL, CODE_VALUE);
		this.response.put(TYPE_LABEL, TYPE_VALUE);
		this.response.put(MESSAGE_LABEL, exception.getMessage());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).type(MediaType.APPLICATION_JSON).build();
	}

}
