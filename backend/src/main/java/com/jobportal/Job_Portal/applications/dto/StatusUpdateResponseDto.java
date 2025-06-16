package com.jobportal.Job_Portal.applications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdateResponseDto {
    private Long applicationId;
    private String oldStatus;
    private String newStatus;
    private String message;
}
