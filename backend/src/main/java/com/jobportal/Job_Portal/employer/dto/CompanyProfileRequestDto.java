package com.jobportal.Job_Portal.employer.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileRequestDto {

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String bio;

    @NotBlank(message = "Location is required")
    private String location;
}
