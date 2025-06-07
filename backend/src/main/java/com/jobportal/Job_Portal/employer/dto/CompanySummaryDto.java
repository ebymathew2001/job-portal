package com.jobportal.Job_Portal.employer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySummaryDto {
    private Long id;
    private String companyName;
    private String location;
}
