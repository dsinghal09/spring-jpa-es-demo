package com.learning.spring.es.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.spring.es.data.entity.Employee;
import com.learning.spring.es.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees")
	public ResponseEntity<Object> getAllEmployees() {
		List<Employee> employees = employeeService.getEmployees();
		if (employees.isEmpty()) {
			return new ResponseEntity<>("No employee found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Object> getEmployeeById(@PathVariable int id) {
		Optional<Employee> optEmployee = employeeService.getEmployeeById(id);
		if (optEmployee.isPresent()) {
			return new ResponseEntity<>(optEmployee.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No employee found for given id " + id, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/employees")
	public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
		Optional<Employee> employee = employeeService.getEmployeeById(id);
		if (employee.isPresent()) {
			employeeService.deleteEmployee(employee.get());
			return new ResponseEntity<>("Deleted employee successfully!", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("No Employee found with the given id " + id, HttpStatus.NOT_FOUND);
		}
	}
}
