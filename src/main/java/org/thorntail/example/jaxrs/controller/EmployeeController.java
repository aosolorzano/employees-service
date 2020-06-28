package org.thorntail.example.jaxrs.controller;

import java.net.URI;
import java.util.Objects;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.logging.Logger;
import org.thorntail.example.jaxrs.model.Employee;
import org.thorntail.example.jaxrs.service.EmployeeReaderService;
import org.thorntail.example.jaxrs.service.EmployeeWriterService;

/**
 * 
 * @author Andres Solorzano
 */
@Path("/employees")
public class EmployeeController {

	public static final String API_VERSION = "/v050/";
	private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);

	@EJB
	private EmployeeReaderService employeeReaderService;

	@EJB
	private EmployeeWriterService employeeWriterService;

	@GET
	@Path(API_VERSION)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		LOGGER.debug("findAll()");
		return Response.ok(this.employeeReaderService.findAll()).build();
	}

	@GET
	@Path(API_VERSION + "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		LOGGER.debug("findById() -> " + id);
		return Response.ok(this.employeeReaderService.findById(id)).build();
	}

	@POST
	@Path(API_VERSION)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Valid Employee employee) {
		LOGGER.debug("create() -> " + employee);
		if (this.employeeReaderService.exists(employee.getId())) {
			return Response.status(Status.CONFLICT).build();
		}
		this.employeeWriterService.create(employee);
		URI location = UriBuilder.fromResource(EmployeeController.class).path("/v040/{id}")
				.resolveTemplate("id", employee.getId()).build();
		return Response.created(location).build();
	}

	@PUT
	@Path(API_VERSION + "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, @Valid Employee employee) {
		LOGGER.debug("update() -> " + employee);
		if (!Objects.equals(id, employee.getId())) {
			throw new BadRequestException(
					"El ID del empleado debe coincidir con el ID del parametro enviado en la peticion.");
		}

		Employee actualEmployee = this.employeeReaderService.findById(id);
		if (!Objects.equals(actualEmployee.getId(), employee.getId())) {
			throw new BadRequestException(
					"El ID del empleado a actualizar, no concuerda con el ID del empleado de la peticion.");
		}

		this.employeeWriterService.update(employee);
		return Response.ok().build();
	}

	@DELETE
	@Path(API_VERSION + "{id}")
	public Response delete(@PathParam("id") Long id) {
		LOGGER.debug("delete() -> " + id);
		if (!this.employeeReaderService.exists(id)) {
			return Response.status(Status.NOT_FOUND).build();
		}
		this.employeeWriterService.delete(id);
		return Response.ok().build();
	}

}
