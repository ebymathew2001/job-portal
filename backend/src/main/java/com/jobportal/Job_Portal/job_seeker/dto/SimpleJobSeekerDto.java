package com.jobportal.Job_Portal.job_seeker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJobSeekerDto {

    private String fullName;
    private String resumeUrl;

}
