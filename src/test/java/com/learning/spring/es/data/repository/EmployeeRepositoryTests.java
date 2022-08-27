package com.learning.spring.es.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.learning.spring.es.data.entity.Employee;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TestEntityManager entityManager;
	
	private Employee employee;
	
	@BeforeEach
	public void setup() {
		employee = stubEmployee("Test","Test","test@test.com");
	}

	@Test
	public void test_GetEmployeeById() {
		employee = employeeRepository.save(employee);
		Employee employeeResponse = null;
		Optional<Employee> employeeDB = employeeRepository.findById(employee.getEmpId());
		if (employeeDB.isPresent()) {
			employeeResponse = employeeDB.get();
		}
		assertThat(employeeResponse).isNotNull();
		assertThat(employeeResponse.getFirstName()).matches(employee.getFirstName());
	}

	@Test
	public void test_GetAllEmployees() {
		employee = employeeRepository.save(employee);
		entityManager.persist(employee);
		List<Employee> employees = employeeRepository.findAll();
		assertThat(employees.size()).isGreaterThanOrEqualTo(1);
	}
	
	@Test
	public void test_DeleteEmployeeById() {
		employee = employeeRepository.save(employee);
		entityManager.persist(employee);
		employeeRepository.deleteById(employee.getEmpId());
	}
	
	private Employee stubEmployee(String firstName, String lastName, String mail) {
		return Employee.builder().firstName(firstName).lastName(lastName).mail(mail).build();
	}
}
