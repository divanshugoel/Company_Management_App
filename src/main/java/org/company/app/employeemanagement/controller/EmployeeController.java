package org.company.app.employeemanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.company.app.employeemanagement.entity.Employee;
import org.company.app.employeemanagement.requestdto.EmployeeRequestDTO;
import org.company.app.employeemanagement.responsedto.EmployeeResponseDTO;
import org.company.app.employeemanagement.serviceimpl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	EmployeeServiceImpl employeeService;

	@Autowired
	public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
		this.employeeService = employeeServiceImpl;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO)
			throws Exception {
		LOGGER.info("Inside createEmployee");
		Employee createdEmployee = employeeService.createEmployee(employeeRequestDTO);
		EmployeeResponseDTO employeeResponseDTO = prepareResponse(createdEmployee);

		final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/employee/{id}").build()
				.expand(createdEmployee.getId()).toUri();
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		LOGGER.info("Exiting createEmployee");
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(employeeResponseDTO);

	}

	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) throws Exception {
		LOGGER.info("Inside updateEmployee");
		if (employeeRequestDTO.getId() == null) {
			LOGGER.info("Exiting updateEmployee");
			throw new Exception("employee id cannot be null");
		}
		Employee updatedEmployee = employeeService.updateEmployee(employeeRequestDTO);
		EmployeeResponseDTO employeeResponseDTO = prepareResponse(updatedEmployee);
		LOGGER.info("Exiting updateEmployee");
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable("id") Long employeeId) throws Exception {
		LOGGER.info("Inside getEmployee");
		Employee employee = employeeService.getEmployee(employeeId);
		EmployeeResponseDTO employeeResponseDTO = prepareResponse(employee);
		LOGGER.info("Exiting getEmployee");
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDTO);
	}

	@GetMapping("/all")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
		LOGGER.info("Inside getAllEmployees");
		List<EmployeeResponseDTO> employeeResponseDTOList = new ArrayList<>();
		List<Employee> allEmployeesList = employeeService.getAllEmployees();
		for (Employee employee : allEmployeesList) {
			EmployeeResponseDTO employeeResponseDTO = prepareResponse(employee);
			employeeResponseDTOList.add(employeeResponseDTO);
		}
		LOGGER.info("Exiting getAllEmployees");
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDTOList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId) throws Exception {
		LOGGER.info("Inside deleteEmployee");
		employeeService.deleteEmployee(employeeId);
		LOGGER.info("Exiting deleteEmployee");
		return ResponseEntity.status(HttpStatus.OK).body("employee deleted with id = " + employeeId);
	}

	private EmployeeResponseDTO prepareResponse(Employee createdEmployee) {
		EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
		employeeResponseDTO.setEmployeeName(createdEmployee.getEmployeeName());
		employeeResponseDTO.setDesignation(createdEmployee.getDesignation());
		employeeResponseDTO.setId(createdEmployee.getId());
		return employeeResponseDTO;
	}

}
