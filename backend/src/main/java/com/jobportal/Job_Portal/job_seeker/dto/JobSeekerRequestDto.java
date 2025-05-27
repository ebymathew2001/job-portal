package com.jobportal.Job_Portal.job_seeker.dto;

import jakarta.validation.constraints.NotBlank;

public class JobSeekerRequestDto {

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String bio;

    private String skills;

    private String experience;

    private String education;

    private String resumeUrl;

    @NotBlank(message = "Location is required")
    private String location;


}
