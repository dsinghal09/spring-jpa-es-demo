package com.learning.spring.es.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.spring.es.data.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
