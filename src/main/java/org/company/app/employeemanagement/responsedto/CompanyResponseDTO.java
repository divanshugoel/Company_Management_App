package org.company.app.employeemanagement.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyResponseDTO {

	private Long id;
	private String companyName;
	private String hq;

}
