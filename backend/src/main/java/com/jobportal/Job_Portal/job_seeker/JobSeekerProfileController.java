package com.jobportal.Job_Portal.job_seeker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileRequestDto;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import com.jobportal.Job_Portal.job_seeker.dto.ResumeDownloadResponseDto;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-seeker")
public class JobSeekerProfileController {

    private final JobSeekerProfileService jobSeekerProfileService;


    @PostMapping("/jobseeker-profiles")
    public ResponseEntity<JobSeekerProfileResponseDto> createProfile(
            @RequestParam("profile") String profileJson,
            @RequestParam("resume") MultipartFile resumeFile,
            Principal principal) throws JsonProcessingException {

        if (profileJson == null || profileJson.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile data is required.");
        }

        if (resumeFile == null || resumeFile.isEmpty()) {
            throw new IllegalArgumentException("Resume file is required.");
        }

        // Parse JSON string to DTO
        ObjectMapper objectMapper = new ObjectMapper();
        JobSeekerProfileRequestDto requestDto = objectMapper.readValue(profileJson, JobSeekerProfileRequestDto.class);

        // Call service to handle business logic
        JobSeekerProfileResponseDto responseDto = jobSeekerProfileService.createProfile(requestDto, resumeFile, principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/jobseeker-profile/me")
    public ResponseEntity<JobSeekerProfileResponseDto> viewProfile(Principal principal){

        JobSeekerProfileResponseDto jobSeekerProfileResponseDto=jobSeekerProfileService.viewProfile(principal);

        return ResponseEntity.ok(jobSeekerProfileResponseDto);
    }

    @GetMapping("/jobseeker-profiles/resume")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Resource> downloadResume(Principal principal, HttpServletRequest request) {
        ResumeDownloadResponseDto download = jobSeekerProfileService.getResumeForDownload(principal, request);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(download.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + download.getFilename() + "\"")
                .body(download.getResource());
    }




}

