package org.company.app.employeemanagement.requestdto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequestDTO {

	@NotNull(message = "employee name cannot be null ")
	@NotEmpty(message = "employee name cannot be empty ")
	private String employeeName;

	@NotNull(message = "employee designation cannot be null ")
	@NotEmpty(message = "employee designation cannot be empty ")
	private String designation;

	private Long id;

	@NotNull(message = "employee's comapny cannot be null ")
	private Long companyId;
}
