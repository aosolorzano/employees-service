package org.thorntail.example.jaxrs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Andres Solorzano
 */
@Entity
@Table(name = "EMPLOYEE", schema = "EMPLOYEE")
@NamedQueries({
	@NamedQuery(name = "findById", query = "SELECT e FROM Employee e WHERE e.id = :id"),
	@NamedQuery(name = "findAll", query = "SELECT e FROM Employee e"),
	@NamedQuery(name = "countAll", query = "SELECT COUNT(e) FROM Employee e")
})
public class Employee implements Serializable {

	private static final long serialVersionUID = -6657644761581563754L;

	@Id
	@Min(value = 1L)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "NAME", length = 255)
	private String name;

	public Employee() {
		// Nothing to implement
	}
	
	public Employee(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}

}
