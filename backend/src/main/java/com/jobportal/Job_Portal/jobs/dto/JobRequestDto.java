package com.jobportal.Job_Portal.jobs.dto;

import com.jobportal.Job_Portal.jobs.JobType;
import com.jobportal.Job_Portal.jobs.WorkMode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequestDto {

    @NotBlank(message = "Title is needed")
    private String title;

    @NotBlank(message = "Description is needed")
    private String description;

    @NotBlank(message = "Job-type is needed")
    private JobType jobType;

    @NotBlank(message = "WorkMode is needed")
    private WorkMode workMode;

    private String salary;
    
}
