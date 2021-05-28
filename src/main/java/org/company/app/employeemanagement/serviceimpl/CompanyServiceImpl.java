package org.company.app.employeemanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.company.app.employeemanagement.entity.Company;
import org.company.app.employeemanagement.repository.CompanyRepository;
import org.company.app.employeemanagement.requestdto.CompanyRequestDTO;
import org.company.app.employeemanagement.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
	CompanyRepository companyRepository;

	@Autowired
	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Override
	public Company createCompany(CompanyRequestDTO companyRequestDTO) throws Exception {
		org.company.app.employeemanagement.entity.Company company = new Company();
		company.setCompanyName(companyRequestDTO.getCompanyName());
		company.setHq(companyRequestDTO.getHq());
		if (existByName(company.getCompanyName())) {
			LOGGER.error("cannot create a company, company already exists");
			throw new Exception("error creating a company, duplicates not allowed");
		}
		companyRepository.save(company);
		LOGGER.info("company created with id = {}", company.getId());
		return company;
	}

	@Override
	public Company updateCompany(CompanyRequestDTO companyRequestDTO) throws Exception {
		Long id = companyRequestDTO.getId();
		if (!companyRepository.existsById(id)) {
			LOGGER.error("cannot update a company,company doesnot exists");
			throw new Exception("error updating a company,company doesnot exists");
		}
		Company companyEntity = companyRepository.findById(id).get();
		if (!companyEntity.getCompanyName().equals(companyRequestDTO.getCompanyName())) {
			if (existByName(companyRequestDTO.getCompanyName())) {
				LOGGER.error("cannot update a company,company with name {} already exists",
						companyRequestDTO.getCompanyName());
				throw new Exception("error updating a company,duplicates not allowed");
			}
		}
		companyEntity.setCompanyName(companyRequestDTO.getCompanyName());
		companyEntity.setHq(companyRequestDTO.getHq());
		companyRepository.save(companyEntity);
		LOGGER.info("company updated with id = {}", companyEntity.getId());
		return companyEntity;
	}

	@Override
	public Company getCompany(Long id) throws Exception {
		Optional<Company> companyEntity = companyRepository.findById(id);
		if (!companyEntity.isPresent()) {
			LOGGER.error("cannot get a company,company doesnot exists");
			throw new Exception("error getting a company,company doesnot exists");
		}
		return companyEntity.get();
	}

	@Override
	public void deleteCompany(Long id) throws Exception {
		if (!companyRepository.existsById(id)) {
			LOGGER.error("cannot deletea company,company doesnot exists");
			throw new Exception("error deleting a company,company doesnot exists");
		}
		companyRepository.deleteById(id);
		LOGGER.info("company deleted with id = {}", id);
	}

	@Override
	public List<Company> getAllCompanies() {
		List<Company> companyList = new ArrayList<>();
		Iterable<Company> companyEntityList = companyRepository.findAll();
		for (Company company : companyEntityList) {
			companyList.add(company);
		}
		return companyList;
	}

	private boolean existByName(String name) {
		Optional<Company> companyEntity = companyRepository.findByCompanyName(name);
		if (companyEntity.isPresent()) {
			return true;
		}
		return false;
	}
}
