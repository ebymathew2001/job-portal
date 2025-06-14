package com.jobportal.Job_Portal.jobs.dto;

import com.jobportal.Job_Portal.employer.dto.CompanySummaryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDto {

    private Long id;

    private String title;

    private String description;


    private String jobType;

    private String workMode;

    private String salary;

    private LocalDateTime createdAt;

    private String jobStatus;

    private CompanySummaryDto company;
}
