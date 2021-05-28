package org.company.app.employeemanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.company.app.employeemanagement.entity.Company;
import org.company.app.employeemanagement.requestdto.CompanyRequestDTO;
import org.company.app.employeemanagement.responsedto.CompanyResponseDTO;
import org.company.app.employeemanagement.serviceimpl.CompanyServiceImpl;
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
@RequestMapping("/api/company")
public class CompanyController {

	CompanyServiceImpl companyService;

	@Autowired
	public CompanyController(CompanyServiceImpl companyServiceImpl) {
		this.companyService = companyServiceImpl;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO)
			throws Exception {
		LOGGER.info("Inside createCompany");
		Company createdCompany = companyService.createCompany(companyRequestDTO);
		CompanyResponseDTO companyResponseDTO = prepareResponse(createdCompany);

		final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/company/{id}").build()
				.expand(createdCompany.getId()).toUri();
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		LOGGER.info("Exiting createCompany");
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(companyResponseDTO);

	}

	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) throws Exception {
		LOGGER.info("Inside updateCompany");
		if (companyRequestDTO.getId() == null) {
			LOGGER.info("Exiting updateCompany");
			throw new Exception("company id cannot be null");
		}
		Company updateCompany = companyService.updateCompany(companyRequestDTO);
		CompanyResponseDTO companyResponseDTO = prepareResponse(updateCompany);
		LOGGER.info("Exiting updateCompany");
		return ResponseEntity.status(HttpStatus.OK).body(companyResponseDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyResponseDTO> getCompany(@PathVariable("id") Long companyId) throws Exception {
		LOGGER.info("Inside getCompany");
		Company company = companyService.getCompany(companyId);
		CompanyResponseDTO companyResponseDTO = prepareResponse(company);
		LOGGER.info("Exiting getCompany");
		return ResponseEntity.status(HttpStatus.OK).body(companyResponseDTO);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CompanyResponseDTO>> getAllCompany() {
		LOGGER.info("Inside getAllCompany");
		List<CompanyResponseDTO> companyResponseDTOList = new ArrayList<>();
		List<Company> allCompanyList = companyService.getAllCompanies();
		for (Company company : allCompanyList) {
			CompanyResponseDTO companyResponseDTO = prepareResponse(company);
			companyResponseDTOList.add(companyResponseDTO);
		}
		LOGGER.info("Exiting getAllCompany");
		return ResponseEntity.status(HttpStatus.OK).body(companyResponseDTOList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCompany(@PathVariable("id") Long companyId) throws Exception {
		LOGGER.info("Inside deleteCompany");
		companyService.deleteCompany(companyId);
		LOGGER.info("Exiting deleteCompany");
		return ResponseEntity.status(HttpStatus.OK).body("company deleted with id = " + companyId);
	}

	private CompanyResponseDTO prepareResponse(Company createdCompany) {
		CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
		companyResponseDTO.setCompanyName(createdCompany.getCompanyName());
		companyResponseDTO.setHq(createdCompany.getHq());
		companyResponseDTO.setId(createdCompany.getId());
		return companyResponseDTO;
	}

}
