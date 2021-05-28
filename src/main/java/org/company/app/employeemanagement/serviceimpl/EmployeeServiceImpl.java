package org.company.app.employeemanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.company.app.employeemanagement.entity.Company;
import org.company.app.employeemanagement.entity.Employee;
import org.company.app.employeemanagement.repository.CompanyRepository;
import org.company.app.employeemanagement.repository.EmployeeRepository;
import org.company.app.employeemanagement.requestdto.EmployeeRequestDTO;
import org.company.app.employeemanagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	EmployeeRepository employeeRepository;
	CompanyRepository companyRepository;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
		this.employeeRepository = employeeRepository;
		this.companyRepository = companyRepository;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public Employee createEmployee(EmployeeRequestDTO employeeRequestDTO) throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeName(employeeRequestDTO.getEmployeeName());
		employee.setDesignation(employeeRequestDTO.getDesignation());
		if (existByName(employee.getEmployeeName())) {
			LOGGER.error("cannot create an employee, employee already exists");
			throw new Exception("error creating an employee, duplicates not allowed");
		}
		Optional<Company> companyEntity = companyRepository.findById(employeeRequestDTO.getCompanyId());
		if (!companyEntity.isPresent()) {
			LOGGER.error("cannot create an employee, company with id {} doesnot exists",
					employeeRequestDTO.getCompanyId());
			throw new Exception("error creating an employee, company doesnot exist");
		}
		employee.setCompany(companyEntity.get());
		employeeRepository.save(employee);
		LOGGER.info("employee created with id = {}", employee.getId());
		return employee;

	}

	@Override
	public Employee updateEmployee(EmployeeRequestDTO employeeRequestDTO) throws Exception {
		Long id = employeeRequestDTO.getId();
		if (!employeeRepository.existsById(id)) {
			LOGGER.error("cannot update an employee,employee doesnot exists");
			throw new Exception("error updating an employee, employee doesnot exists");
		}
		Employee employeeEntity = employeeRepository.findById(id).get();
		if (!employeeEntity.getEmployeeName().equals(employeeRequestDTO.getEmployeeName())) {
			if (existByName(employeeRequestDTO.getEmployeeName())) {
				LOGGER.error("cannot update an employee, employee with name {} already exists",
						employeeRequestDTO.getEmployeeName());
				throw new Exception("error updating an employee, duplicates not allowed");
			}
		}
		employeeEntity.setDesignation(employeeRequestDTO.getDesignation());
		employeeEntity.setEmployeeName(employeeRequestDTO.getEmployeeName());
		employeeRepository.save(employeeEntity);
		LOGGER.info("employee updated with id = {}", employeeEntity.getId());
		return employeeEntity;
	}

	@Override
	public Employee getEmployee(Long id) throws Exception {
		Optional<Employee> employeeEntity = employeeRepository.findById(id);
		if (!employeeEntity.isPresent()) {
			LOGGER.error("cannot get an employee, employee doesnot exists");
			throw new Exception("error getting an employee, employee doesnot exists");
		}
		return employeeEntity.get();
	}

	@Override
	public void deleteEmployee(Long id) throws Exception {
		if (!employeeRepository.existsById(id)) {
			LOGGER.error("cannot delete an employee,employee doesnot exists");
			throw new Exception("error deleting an employee, employee doesnot exists");
		}
		employeeRepository.deleteById(id);
		LOGGER.info("employee deleted with id = {}", id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = new ArrayList<>();
		Iterable<Employee> employeeEntityList = employeeRepository.findAll();
		for (Employee employee : employeeEntityList) {
			employeeList.add(employee);
		}
		return employeeList;
	}

	private boolean existByName(String name) {
		Optional<Employee> employeeEntity = employeeRepository.findByEmployeeName(name);
		if (employeeEntity.isPresent()) {
			return true;
		}
		return false;
	}
}
