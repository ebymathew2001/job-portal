package com.jobportal.Job_Portal.applications.dto;


import com.jobportal.Job_Portal.applications.ApplicationStatus;
import com.jobportal.Job_Portal.jobs.dto.SimpleJobDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApplicationSummaryDto {
    private Long id;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private SimpleJobDto simpleJobDto;
}
