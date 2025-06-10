package com.jobportal.Job_Portal.jobs;

import com.jobportal.Job_Portal.jobs.dto.JobRequestDto;
import com.jobportal.Job_Portal.jobs.dto.JobResponseDto;
import com.jobportal.Job_Portal.jobs.dto.JobStatusUpdateDTO;
import com.jobportal.Job_Portal.jobs.dto.JobSummaryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class JobController {

    private JobService jobService;

    @PostMapping("/employer/jobs")
    public ResponseEntity<JobResponseDto> createJob(@Valid @RequestBody JobRequestDto jobRequestDto, Principal principal){
        JobResponseDto jobResponseDto=jobService.createJob(jobRequestDto,principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseDto);
    }

    @PutMapping("/employer/jobs/{id}")
    public ResponseEntity<JobResponseDto> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequestDto jobRequestDto, Principal principal){
        JobResponseDto jobResponseDto=jobService.updateJob(id,jobRequestDto,principal);

        return ResponseEntity.ok(jobResponseDto);
    }

    @PatchMapping("/employer/jobs/{id}/status")
    public ResponseEntity<JobResponseDto> updateStatus(@PathVariable Long id, @Valid @RequestBody JobStatusUpdateDTO jobStatusUpdateDTO,Principal principal){
        JobResponseDto jobResponseDto=jobService.updateStatus(id,jobStatusUpdateDTO,principal);

        return ResponseEntity.ok(jobResponseDto);
    }

    @PreAuthorize("hasAnyRole('EMPLOYER','JOBSEEKER','ADMIN')")
    @GetMapping("/jobs")
    public ResponseEntity<List<JobSummaryDto>> getAllActiveJobs() {
        List<JobSummaryDto> activeJobs = jobService.getAllActiveJobSummaries();
        return ResponseEntity.ok(activeJobs);
    }


    @PreAuthorize("hasAnyRole('EMPLOYER','JOBSEEKER','ADMIN')")
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobResponseDto> getJobById(@PathVariable Long id){
        JobResponseDto jobResponseDto=jobService.getJobById(id);
        return ResponseEntity.ok(jobResponseDto);
    }

    @GetMapping("/employer/jobs")
    public ResponseEntity<List<JobSummaryDto>> getJobsByEmployer(Principal principal){
        List<JobSummaryDto> jobSummaryDto=jobService.getJobsByEmployer(principal);

        return ResponseEntity.ok(jobSummaryDto);
    }









}
