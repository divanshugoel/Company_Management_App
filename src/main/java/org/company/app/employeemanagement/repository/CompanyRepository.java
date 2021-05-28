package org.company.app.employeemanagement.repository;

import java.util.Optional;

import org.company.app.employeemanagement.entity.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends Neo4jRepository<Company, Long> {

	public Optional<Company> findByCompanyName(String name);
}
