package org.company.app.employeemanagement.service;

import java.util.List;

import org.company.app.employeemanagement.entity.Employee;
import org.company.app.employeemanagement.requestdto.EmployeeRequestDTO;

public interface EmployeeService {

	public Employee createEmployee(EmployeeRequestDTO employeeRequestDTO) throws Exception;

	public Employee updateEmployee(EmployeeRequestDTO employeeRequestDTO) throws Exception;

	public Employee getEmployee(Long id) throws Exception;

	public void deleteEmployee(Long id) throws Exception;

	public List<Employee> getAllEmployees();

}
