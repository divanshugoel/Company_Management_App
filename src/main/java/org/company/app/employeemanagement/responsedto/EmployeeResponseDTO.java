package org.company.app.employeemanagement.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponseDTO {

	private Long id;
	private String employeeName;
	private String designation;

}
