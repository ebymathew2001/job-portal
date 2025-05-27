package com.jobportal.Job_Portal.job_seeker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerResponseDto {

    private Long id;

    private String fullName;

    private String bio;

    private String skills;

    private String experience;

    private String education;

    private String resumeUrl;

    private String location;

    private LocalDateTime updatedAt;

}
