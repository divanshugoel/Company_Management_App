package org.company.app.employeemanagement.entity;


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
@NodeEntity("Employee")
public class Employee {
	@Id
	@GeneratedValue
	private Long id;
	private String employeeName;
	private String designation;
	@Relationship(type ="WORKED_IN")
	private Company company;
	
}
