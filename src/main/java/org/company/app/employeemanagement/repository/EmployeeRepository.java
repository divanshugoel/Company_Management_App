package org.company.app.employeemanagement.repository;

import java.util.Optional;

import org.company.app.employeemanagement.entity.Employee;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends Neo4jRepository<Employee, Long> {

	public Optional<Employee> findByEmployeeName(String name);
}
