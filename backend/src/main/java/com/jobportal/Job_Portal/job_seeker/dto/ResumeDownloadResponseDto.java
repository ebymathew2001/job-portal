package com.jobportal.Job_Portal.job_seeker.dto;

import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResumeDownloadResponseDto {
    private Resource resource;
    private String filename;
    private String contentType;

}
