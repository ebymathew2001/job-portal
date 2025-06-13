package com.jobportal.Job_Portal.jobs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJobDto {
    private Long id;
    private String title;
    private String companyName;
}
