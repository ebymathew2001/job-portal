package com.jobportal.Job_Portal.applications.dto;

import com.jobportal.Job_Portal.applications.ApplicationStatus;
import com.jobportal.Job_Portal.jobs.dto.SimpleJobDto;
import com.jobportal.Job_Portal.job_seeker.dto.SimpleJobSeekerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDto {
    private Long id;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    private SimpleJobDto job; // Use full JobResponseDto when needed
    private SimpleJobSeekerDto jobSeeker; // Use full JobSeekerProfileResponseDto when needed
}
