package com.learning.spring.es.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.spring.es.data.entity.Employee;
import com.learning.spring.es.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;
	
	private static final String URL = "/employees/1";
	
	private static final String POST_URL = "/employees";

	@Test
	void test_GetEmployeeById_Success() throws Exception {
		Employee mockedEmployee = stubEmployee("Deepak", "Singhal", "abc.abc@abc.com");
		mockedEmployee.setEmpId(1);
		Optional<Employee> employee = Optional.of(mockedEmployee);
		when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(employee);
		mockMvc.perform(get(URL)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("empId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("firstName").value("Deepak"))
				.andExpect(MockMvcResultMatchers.jsonPath("lastName").value("Singhal")).andDo(print());
	}
	
	@Test
	void test_GetEmployeeById_Failure() throws Exception {
		mockMvc.perform(get(URL)).andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	void test_CreateEmployee() throws Exception {
		Employee mockedEmployee = stubEmployee("Test1", "TestSave", "abc.abc@abc.com");
		mockedEmployee.setEmpId(1);
		when(employeeService.saveEmployee(Mockito.any())).thenReturn(mockedEmployee);

		mockMvc.perform(post(POST_URL).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockedEmployee))).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("empId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("firstName").value("Test1"))
				.andExpect(MockMvcResultMatchers.jsonPath("lastName").value("TestSave")).andDo(print());
	}

	@Test
	void test_DeleteEmployee_Success() throws Exception {
		Employee mockedEmployee = stubEmployee("Deepak", "Singhal", "abc.abc@abc.com");
		mockedEmployee.setEmpId(1);
		Optional<Employee> employee = Optional.of(mockedEmployee);
		when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(employee);
		doNothing().when(employeeService).deleteEmployee(Mockito.any());
		mockMvc.perform(delete(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andDo(print());
	}
	
	@Test
	void test_DeleteEmployee_Failure() throws Exception {
		mockMvc.perform(delete(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print());
	}
	
	private Employee stubEmployee(String firstName, String lastName, String mail) {
		return Employee.builder().firstName(firstName).lastName(lastName).mail(mail).createdAt(new Date())
				.updatedAt(new Date()).build();
	}
}
