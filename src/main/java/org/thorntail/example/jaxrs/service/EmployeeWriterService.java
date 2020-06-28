package org.thorntail.example.jaxrs.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;
import org.thorntail.example.jaxrs.model.Employee;

/**
 * 
 * @author Andres Solorzano
 */
@Stateless
public class EmployeeWriterService {

	private static final Logger LOGGER = Logger.getLogger(EmployeeWriterService.class);

	@PersistenceContext(unitName = "EmployeesWriterPU")
	private EntityManager entityManager;

	public void create(Employee employee) {
		employee.setId(this.countAll() + 1L);
		this.entityManager.persist(employee);
	}

	public Employee update(Employee employee) {
		return this.entityManager.merge(employee);
	}

	public void delete(Long id) {
		LOGGER.debug("delete() -> " + id);
		Employee employee = this.entityManager.getReference(Employee.class, id);
		this.entityManager.remove(employee);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private Long countAll() {
		TypedQuery<Long> query = this.entityManager.createNamedQuery("countAll", Long.class);
		Long result = query.getSingleResult();
		LOGGER.debug("countAll() -> " + result);
		return result;
	}

}
