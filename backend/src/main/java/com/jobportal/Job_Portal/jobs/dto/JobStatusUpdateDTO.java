package com.jobportal.Job_Portal.jobs.dto;

import com.jobportal.Job_Portal.jobs.JobStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobStatusUpdateDTO {

    @NotNull(message = "Job status is required")
    private JobStatus jobStatus;
}
