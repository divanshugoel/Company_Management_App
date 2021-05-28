package org.company.app.employeemanagement.entity;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity("Company")
public class Company {
	@Id
	@GeneratedValue
	private Long id;
	private String companyName;
	private String hq;
	@Relationship(type ="WORKED_IN", direction = Relationship.INCOMING)
	private List<Employee> employees;
}
