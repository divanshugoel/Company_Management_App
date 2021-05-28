package org.company.app.employeemanagement.service;

import java.util.List;

import org.company.app.employeemanagement.entity.Company;
import org.company.app.employeemanagement.requestdto.CompanyRequestDTO;

public interface CompanyService {

	public Company createCompany(CompanyRequestDTO companyRequestDTO) throws Exception;

	public Company updateCompany(CompanyRequestDTO companyRequestDTO) throws Exception;

	public Company getCompany(Long id) throws Exception;

	public void deleteCompany(Long id) throws Exception;

	public List<Company> getAllCompanies();

}
