package com.jobportal.Job_Portal.applications.dto;


import com.jobportal.Job_Portal.applications.ApplicationStatus;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import com.jobportal.Job_Portal.jobs.dto.JobResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployerApplicationDetailsDto {
    private Long id;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private JobSeekerProfileResponseDto jobSeekerProfileResponseDto;
}
