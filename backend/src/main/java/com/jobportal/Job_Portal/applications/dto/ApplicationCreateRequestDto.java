package com.jobportal.Job_Portal.applications.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCreateRequestDto {
    @NotNull(message = "Job ID is required")
    private Long jobId;
}
