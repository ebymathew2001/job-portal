package com.jobportal.Job_Portal.applications;

import com.jobportal.Job_Portal.applications.dto.*;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/job-seeker/applications")
    public ResponseEntity<ApplicationResponseDto> createApplication(@Valid @RequestBody ApplicationCreateRequestDto applicationRequestDto, Principal principal){

        ApplicationResponseDto applicationResponseDto=applicationService.createApplication(applicationRequestDto,principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponseDto);

    }

    @GetMapping("/job-seeker/applications")
    public ResponseEntity<List<ApplicationSummaryDto>> getMyApplications(Principal principal){

        List<ApplicationSummaryDto> applicationSummaryDto=applicationService.getMyApplications(principal);

        return ResponseEntity.ok(applicationSummaryDto);
    }

    @GetMapping("/employer/jobs/{jobId}/applications")
    public ResponseEntity<List<EmployerApplicationSummaryDto>> getApplicationsForJob(@PathVariable Long jobId,
            Principal principal) {

        List<EmployerApplicationSummaryDto> applicationList = applicationService.getApplicationsForJob(jobId, principal);

        return ResponseEntity.ok(applicationList);
    }

    @GetMapping("/job-seeker/applications/{id}")
    public ResponseEntity<JobSeekerApplicationDetailsDto> getApplicationDetailsById(@PathVariable Long id,Principal principal){

        JobSeekerApplicationDetailsDto jobSeekerProfileResponseDto=applicationService.getApplicationDetailsById(id,principal);

        return ResponseEntity.ok(jobSeekerProfileResponseDto);
    }

    @GetMapping("/employer/jobs/{jobId}/applications/{applicationId}")
    public ResponseEntity<EmployerApplicationDetailsDto> getApplicationDetailsForEmployer(
            @PathVariable Long jobId,
            @PathVariable Long applicationId,
            Principal principal) {

        EmployerApplicationDetailsDto detailsDto = applicationService.getApplicationDetailsForEmployer(jobId, applicationId, principal);

        return ResponseEntity.ok(detailsDto);
    }


    @PatchMapping("/employer/jobs/{jobId}/applications/{applicationId}")
    public ResponseEntity<StatusUpdateResponseDto> updateStatus(@PathVariable Long jobId,
                    @PathVariable Long applicationId,
                    @RequestBody ApplicationStatusUpdateRequestDto applicationStatusUpdateRequestDto
                    ,Principal principal){

        StatusUpdateResponseDto statusUpdateResponseDto=applicationService.updateStatus(jobId,applicationId,principal,applicationStatusUpdateRequestDto);

        return ResponseEntity.ok(statusUpdateResponseDto);
    }







}
