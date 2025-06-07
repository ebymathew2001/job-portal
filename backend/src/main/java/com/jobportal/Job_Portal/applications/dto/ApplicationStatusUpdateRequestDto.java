package com.jobportal.Job_Portal.applications.dto;

import com.jobportal.Job_Portal.applications.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatusUpdateRequestDto {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;
}
