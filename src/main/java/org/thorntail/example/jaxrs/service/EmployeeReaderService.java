package org.thorntail.example.jaxrs.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.thorntail.example.jaxrs.model.Employee;

/**
 * 
 * @author Andres Solorzano
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeReaderService {


	@PersistenceContext(unitName = "EmployeesReaderPU")
	private EntityManager entityManager;

	public Employee findById(Long id) {
		TypedQuery<Employee> query = this.entityManager.createNamedQuery("findById", Employee.class);
		query.setParameter("id", id);
		return query.getSingleResult(); // COULD THROW ENTITY NOT FOUND EXCEPTION
	}

	public List<Employee> findAll() {
		TypedQuery<Employee> query = this.entityManager.createNamedQuery("findAll", Employee.class);
		return query.getResultList();
	}

	public Boolean exists(Long id) {
		Boolean exist = Boolean.FALSE;
		Employee employee = this.entityManager.find(Employee.class, id); // FIND NOT THROW ENTITY NOT FOUND EXCEPTION
		if (Objects.nonNull(employee)) {
			exist = Boolean.TRUE;
		}
		return exist;
	}

}
